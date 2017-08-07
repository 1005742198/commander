package com.huajin.commander.manager.controller;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.huajin.commander.common.ReliableEvent;
import com.huajin.commander.manager.domain.GlobalEventEntity;
import com.huajin.commander.manager.repository.GlobalEventRepository;
import com.huajin.commander.manager.service.EventManagerService;


/**
 * 事件管理器 controller.
 * 
 * @author bo.yang
 */
@RestController
@RequestMapping("/v1")
public class EventManagerController {

	@Autowired
	private EventManagerService eventManagerService;
	
	@Autowired
	private GlobalEventRepository eventRepository;
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	/**
	 * 通知远程的事件管理器创建"可靠事件"。
	 * 
	 * @param event 在远程事件管理器上创建的<b>可靠事件</b>
	 */
	@RequestMapping(path="/event", method=RequestMethod.POST)
	public Long createRemoteEvent(@RequestBody ReliableEvent reliableEvent){
		log.debug("To Create event {}", reliableEvent.getEventId());
		
		ReliableEvent event = eventManagerService.createReliableEvent(reliableEvent);
		return event.getEventId();
	}
	
	/**
	 * 通知远程的事件管理器创建一组"可靠事件"。
	 * 
	 * @param eventList 在远程事件管理器上创建的一组<b>可靠事件</b>
	 * @return 成功保存的事件个数
	 */
	@RequestMapping(path="/events", method=RequestMethod.POST)
	public List<Long> createRemoteEventList(@RequestBody List<ReliableEvent> reliableEventList){
		return eventManagerService.createReliableEventList(reliableEventList);
	}
	
	/**
	 * 向远程的事件管理器"确认"指定事件的发送
	 * 
	 * @param eventIdList 要确认发送的事件的id
	 */
	@RequestMapping(path="/sendevents", method=RequestMethod.POST)
	public List<Long> confirmSend(@RequestParam("ids") List<Long> eventIdList){
		log.debug("To confirm send events: {}", eventIdList);
		return eventManagerService.confirmSend(eventIdList);
	}
	
	/**
	 * 向远程的事件管理器"取消"指定事件的发送
	 * 
	 * @param eventIdList 要取消发送的事件的id
	 */
	@RequestMapping(path="/cancelevents", method=RequestMethod.POST)
	public List<Long> cancelSend(@RequestParam("ids") List<Long> eventIdList){
		log.debug("To cancel send events: {}", eventIdList);
		return eventManagerService.cancelSend(eventIdList);
	}
	
	/**
	 * 列出所有的“全局可靠事件”。
	 * 
	 * @param reliableEvent
	 * @return
	 */
	@RequestMapping(path="/events", method=RequestMethod.GET)
	public List<GlobalEventEntity> listEvents(ReliableEvent reliableEvent){
		LinkedList<GlobalEventEntity> allEvents = new LinkedList<GlobalEventEntity>();
		Iterable<GlobalEventEntity> result = eventRepository.findAll();
		result.forEach(entity -> allEvents.add(entity));
		return allEvents;
	}
	
}
