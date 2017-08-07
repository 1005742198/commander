package com.huajin.commander.client;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.huajin.commander.client.domain.LocalEventEntity;
import com.huajin.commander.client.repository.LocalEventDao;
import com.huajin.commander.client.repository.RepositoryUtils;
import com.huajin.commander.client.service.IdService;
import com.huajin.commander.common.ReliableEvent;
import com.huajin.commander.common.ReliableEventStatus;
import com.huajin.commander.common.ReliableEventType;

/**
 * 可靠事件管理器的客户端API实现。
 * 
 * <p>主要功能是
 * <ol>
 * <li> 记录事件到本地事件表 {@link #createLocalEvent(Object)}
 * <li> 通知事件管理器创建事件
 * <li> 确认提交事件
 * <li> 取消事件发送
 * </ol>
 * 参考 {@link ReliableEventManagerClient}
 * 
 * @author bo.yang
 */
@Component
public class ReliableEventManagerClientImpl implements ReliableEventManagerClient {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private LocalEventDao eventRepository;
	
	@Autowired
	private ReliableEventManagerAPI eventManagerAPI;
	
	@Autowired
	@Qualifier("snowflakeIdService")
	private IdService idService;	// for Event Id
	
	// === for composing service id like 'hostname:port:application-name'
	@Value("${spring.application.name:}")
	private String applicationName;	// for service id
	
	@Value("${eureka.instance.hostname:}")
	private String hostname; 		// for service id
	
	@Value("${server.port:}")
	private String port;			// for service id
	// === end of composing service id

	private ThreadLocal<List<LocalEventEntity>> threadCreatedEvents = new ThreadLocal<>();
	
	
	/**
	 * 记录事件到本地事件表
	 * <p>
	 * 保存并准备发布“实体变动”事件，
	 * 实际发布动作的时间要在“所属事务”提交后才会发生，如果事务回滚则会自动取消发布。
	 * 
	 * @param msgBody 变动的实体，因为该实体变动而产生这个事件
	 * @param  
	 */
	private ReliableEvent createLocalEvent(ReliableEventType eventType, Object msgBody) {
		log.debug("Create local reliable-event for entity ({})", msgBody.getClass().getName());
		ReliableEvent reliableEvent = new ReliableEvent();
		reliableEvent.setEventId(idService.nextId());
		reliableEvent.setTypeId(eventType.value());
		reliableEvent.setTypeName(eventType.name());
		reliableEvent.setCreateTime(new Date());
		
		reliableEvent.setMsgBody(msgBody);
		
		reliableEvent.setSource(getServiceId());
		// 依赖事务的 repeatable-read 隔离特性
		reliableEvent.setStatus(ReliableEventStatus.Sended);
		List<LocalEventEntity> localEventList = threadCreatedEvents.get();
		if (localEventList == null) {
			localEventList = new LinkedList<>();
		}
		// 保存实体对象到数据库
		LocalEventEntity localEvent = RepositoryUtils.getEntityFromReliableEvent(reliableEvent);
		eventRepository.insert(localEvent);
		localEventList.add(localEvent);
		threadCreatedEvents.set(localEventList);
		return reliableEvent;
	}
	
	/**
	 * 产生事件的服务id，通常取当前服务的 Eureka Service Id
	 * @return
	 */
	private String getServiceId() {
		String host = this.hostname;
		if (StringUtils.isBlank(this.hostname)){
			try {
				host = InetAddress.getLocalHost().getHostName();
			} catch (UnknownHostException e) {
				log.warn("Can not get localhost name, use 'localhost' as hostname.");
				host = "localhost";
			}
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append(host).append(":").append(port).append(":").append(applicationName);
		return buffer.toString();
	}

	private void createRemoteEvent(List<ReliableEvent> events) {
		log.debug("Create remote reliable-event (eventId={})", 
				events.stream().map(e->String.valueOf(e.getEventId())).collect(Collectors.joining(",")));
		eventManagerAPI.createRemoteEventList(events);
	}

	/**
	 * 从线程本地变量中获取最近创建的可靠事件实体，然后调用事件管理器REST接口。
	 */
	@Override
	public void confirmSend() {
		List<LocalEventEntity> threadCreatedEvents = this.threadCreatedEvents.get();
		if (threadCreatedEvents == null) {
			log.debug("Confirm send (commit) while there is no reliable-event.");
			return;
		}
		String idString = threadCreatedEvents.stream()
				.map(localEvent -> localEvent.getEventId() + "")
				.collect(Collectors.joining(","));
		log.debug("Confirm send (commit) reliable-event (eventId={})", idString);
		// 更新状态
		threadCreatedEvents.forEach(localEvent -> {
			localEvent.setStatus(ReliableEventStatus.Sended.value());
			eventRepository.update(localEvent);
		});
		// 调用远程事件管理器
		List<Long> eventIdList = threadCreatedEvents.stream()
				.map(event->event.getEventId())
				.collect(Collectors.toList());
		try{
			List<Long> ret = eventManagerAPI.confirmSend(eventIdList);
			log.debug("Confirmed events: {}", ret);
		}catch(Exception e){
			log.error("Call remote event-manager to confirm-send failed!", e);
		}
		this.threadCreatedEvents.remove();
	}

	@Override
	public void cancelSend() {
		List<LocalEventEntity> localEventList = this.threadCreatedEvents.get();
		if (localEventList == null) {
			log.debug("Cancel send (rollback) while there is no reliable-event.");
			return;
		}
		String idString = localEventList.stream()
				.map(localEvent -> localEvent.getEventId() + "")
				.collect(Collectors.joining(","));
		log.debug("Cancel send (rollback) reliable-event (eventId={})", idString);
		// 更新状态
		localEventList.forEach(event -> {
			event.setStatus(ReliableEventStatus.Canceled.value());
			eventRepository.update(event);
		});
		// 调用远程事件管理器
		try{
			eventManagerAPI.cancelSend(localEventList.stream()
					.map(event -> event.getEventId())
					.collect(Collectors.toList()));
		}catch(Exception e){
			log.error("Call remote event-manager to cancel-send fail!", e);
		}
		this.threadCreatedEvents.remove();
	}

	@Override
	public List<ReliableEvent> createEvents(ReliableEventType eventType, Object... msgBodyArray) {
		log.debug("Create reliable-event for {} entities.", msgBodyArray.length);

		List<ReliableEvent> reliableEvents = new LinkedList<>();
		for (Object msgBody : msgBodyArray) {
			ReliableEvent reliableEvent = createLocalEvent(eventType, msgBody);
			reliableEvents.add(reliableEvent);
		}
		createRemoteEvent(reliableEvents);
		return reliableEvents;
	}
	
}
