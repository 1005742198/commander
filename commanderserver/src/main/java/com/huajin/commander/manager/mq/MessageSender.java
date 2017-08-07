package com.huajin.commander.manager.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.huajin.commander.common.Constants;
import com.huajin.commander.common.ReliableEvent;

/**
 * RabbitMQ 消息发送服务
 * 
 * @author bo.yang
 */
@Component
public class MessageSender {
	
    private final AmqpTemplate amqpTemplate;
    
    private Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    public MessageSender(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }
    
    /**
     * 发送可靠事件到消息代理。
     * 
     * @param reliableEvent 可靠事件
     */
    public void sendEvent(ReliableEvent reliableEvent) {
    	String routingKey = reliableEvent.getTypeName().toLowerCase() + "." + reliableEvent.getEventId();
    	this.amqpTemplate.convertAndSend(Constants.EXCHANGE_RELIABLE_EVENT, routingKey, reliableEvent);
    }

    /**
     * 发送“查询可靠事件状态消息”到消息代理。
     * 
     * @param eventId 查询的事件id
     */
	public void sendQueryStatusMessage(Long eventId) {
		if (eventId == null) {
			throw new IllegalArgumentException("Event Id to query is null!");
		}
		log.debug("Sending query status message for eventId={}", eventId);
		
		this.amqpTemplate.convertAndSend(
				Constants.EXCHANGE_QUERY_STATUS, 
				Constants.QUERY_EVENT_STATUS_QUEUE_NAME, eventId);
	}

}
