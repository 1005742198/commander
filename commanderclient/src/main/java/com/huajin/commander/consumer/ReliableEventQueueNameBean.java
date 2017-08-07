package com.huajin.commander.consumer;

import org.springframework.stereotype.Component;

import com.huajin.commander.common.Constants;
import com.huajin.commander.common.ReliableEventType;

/**
 * 计算队列名称的 bean.
 * 
 * @author bo.yang
 */
@Component
public class ReliableEventQueueNameBean {

	private ReliableEventType eventType;
	
	public String getQueueName() {
		if (eventType == null) {
			throw new ReliableEventConsumerException("ReliableEventType property is null! Should call setEventType() first.");
		}
		String queueName = Constants.RELIABLE_EVENT_QUEUE_NAME_PREFIX + eventType.name().toLowerCase();
		return queueName;
	}

	public ReliableEventType getEventType() {
		return eventType;
	}

	public void setEventType(ReliableEventType eventType) {
		this.eventType = eventType;
	}
	
}
