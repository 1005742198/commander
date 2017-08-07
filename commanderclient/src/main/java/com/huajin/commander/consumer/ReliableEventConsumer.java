package com.huajin.commander.consumer;

import com.huajin.commander.common.ReliableEvent;
import com.huajin.commander.common.ReliableEventType;


public interface ReliableEventConsumer {

	/**
	 * 要消费的事件类型。
	 * @author bo.yang
	 * @date 2017年6月23日 下午7:04:17
	 */
	ReliableEventType getEventType();
	
	boolean processEvent(ReliableEvent reliableEvent);
}
