package com.huajin.commander.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.huajin.commander.common.ReliableEvent;

@FeignClient(name="commander-server")
public interface ReliableEventManagerAPI {

	/**
	 * 通知远程的事件管理器创建"可靠事件"。
	 * 
	 * @param event 在远程事件管理器上创建的<b>可靠事件</b>
	 */
	@RequestMapping(path="/v1/event", method=RequestMethod.POST)
	Long createRemoteEvent(@RequestBody ReliableEvent event);
	
	/**
	 * 通知远程的事件管理器创建一组"可靠事件"。
	 * 
	 * @param events 在远程事件管理器上创建的一组<b>可靠事件</b>
	 */
	@RequestMapping(path="/v1/events", method=RequestMethod.POST)
	List<Long> createRemoteEventList(@RequestBody List<ReliableEvent> eventList);
	
	/**
	 * 向远程的事件管理器"确认"指定事件的发送
	 * 
	 * @param eventId 要确认发送的事件的id
	 */
	@RequestMapping(path="/v1/sendevents", method=RequestMethod.POST)
	List<Long> confirmSend(@RequestParam("ids") List<Long> eventIdList);
	
	/**
	 * 向远程的事件管理器"取消"指定事件的发送
	 * 
	 * @param eventId 要取消发送的事件的id
	 */
	@RequestMapping(path="/v1/cancelevents", method=RequestMethod.POST)
	public List<Long> cancelSend(@RequestParam("ids") List<Long> eventId);

}
