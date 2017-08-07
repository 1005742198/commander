package com.huajin.commander.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.huajin.commander.common.ReliableEvent;


@Component
public class ReliableEventConsumerClientImpl implements ReliableEventConsumerClient {

	private ReliableEventConsumer consumer;
	private ReliableEventRepeatChecker repeatChecker;
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ReliableEventQueueNameBean queueNameBean;
	
	@Override
	public void registerConsumer(ReliableEventConsumer consumer) {
		if (this.consumer == null) {
			this.consumer = consumer;
		} else {
			throw new IllegalArgumentException("Consumer already registered!");
		}
		// 绑定队列
		queueNameBean.setEventType(consumer.getEventType());
	}

	@RabbitListener(queues="#{reliableEventQueueNameBean.queueName}")
	public void onMessage(ReliableEvent reliableEvent) {
		log.debug("接收到了创建动态的消息：{}", reliableEvent);
		
        if (repeatChecker.isProcessed(reliableEvent)) {
        	log.info("ReliableEvent already be processed!");
        } else {
        	this.consumer.processEvent(reliableEvent);
        }
	}

}
