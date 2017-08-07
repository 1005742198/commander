package com.huajin.commander.manager.service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huajin.commander.common.ReliableEvent;
import com.huajin.commander.common.ReliableEventStatus;
import com.huajin.commander.manager.domain.GlobalEventEntity;
import com.huajin.commander.manager.exception.EventNotFoundException;
import com.huajin.commander.manager.mq.MessageSender;
import com.huajin.commander.manager.repository.GlobalEventRepository;
import com.huajin.commander.manager.repository.RepositoryUtils;

@Service
public class EventManagerService {
	
	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private GlobalEventRepository globalEventRepository;
	
	@Autowired
	private MessageSender msgService;
	

	/**
	 * 创建"可靠事件"。
	 * 
	 * @param reliableEvent 在远程事件管理器上创建的<b>可靠事件</b>
	 * 
	 * @return 创建了的事件实体对象
	 */
	public ReliableEvent createReliableEvent(ReliableEvent reliableEvent){
		log.debug("Create reliable-event (eventId={})", reliableEvent.getEventId());
		
		GlobalEventEntity eventEntity = RepositoryUtils.getEntityFromReliableEvent(reliableEvent);
		globalEventRepository.save(eventEntity);
		return reliableEvent;
	}

	public List<Long> createReliableEventList(List<ReliableEvent> reliableEventList) {
		log.debug("Create reliable-event list, size is {}", reliableEventList.size());
		reliableEventList.forEach(event->createReliableEvent(event));
		
		return reliableEventList.stream().map(event->event.getEventId())
				.collect(Collectors.toList());
	}
	
	/**
	 * "确认"指定事件的发送
	 * 
	 * @param eventIdList 要确认发送的事件的id
	 * @return 成功发送到消息队列的“可靠事件”对象的id。
	 */
	@Transactional
	public List<Long> confirmSend(List<Long> eventIdList){
		LinkedList<ReliableEvent> reliableEventList = new LinkedList<>();
		eventIdList.forEach(eventId -> {
			
			log.debug("Confirm sending of reliable-event (eventId={})", eventId);
			
			GlobalEventEntity eventEntity = globalEventRepository.findByEventId(eventId);
			if (eventEntity == null){
				throw new EventNotFoundException(eventId);
			}
			eventEntity.setStatus(ReliableEventStatus.Sended.value());
			
			// 发送到消息队列
			ReliableEvent reliableEvent = RepositoryUtils.getReliableEventFromEntity(eventEntity);
			msgService.sendEvent(reliableEvent);
			
			// 保存全局事件的状态
			globalEventRepository.save(eventEntity);
			reliableEventList.add(reliableEvent);
		});
		
		return eventIdList;
	}
	
	/**
	 * 向远程的事件管理器"取消"指定事件的发送
	 * 
	 * @param eventId 要取消发送的事件的id
	 * @return 取消了的“可靠事件”对象; 如果事件对象不存在则返回异常。
	 */
	public List<Long> cancelSend(List<Long> eventIdList) {
		List<Long> canceledIds = new LinkedList<>();
		eventIdList.forEach(eventId -> {
			log.debug("Cancel sending of reliable-event (eventId={})", eventId);
			// 更新全局事件的状态为取消发送
			GlobalEventEntity eventEntity = globalEventRepository.findByEventId(eventId);
			if (eventEntity == null) {
				log.error("Can not find eventId '{}' to cancel, skip it!", eventId);
				return;
			}
			canceledIds.add(eventId);
			eventEntity.setStatus(ReliableEventStatus.Canceled.value());
			globalEventRepository.save(eventEntity);
		});
		return canceledIds;
	}

}
