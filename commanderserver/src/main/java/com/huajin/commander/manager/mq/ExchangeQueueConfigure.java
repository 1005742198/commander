package com.huajin.commander.manager.mq;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.huajin.commander.common.Constants;
import com.huajin.commander.common.ReliableEventType;

/**
 * 声明 Queue 和 Exchange.
 * @author bo.yang
 * @date 2017年6月23日 下午2:02:32
 */
@Component
public class ExchangeQueueConfigure {

    private Logger log = LoggerFactory.getLogger(getClass());
	
    @Autowired
    private AmqpAdmin amqpAdmin;
    
    @PostConstruct
    public void configQueueExchange(){
    	// 配置状态查询队列
        Queue queue = new Queue(Constants.PROCESSED_QUEUE_NAME, true);	// durable queue
        String declaredQueue = amqpAdmin.declareQueue(queue);
        if (declaredQueue == null) {
            log.error("Declare queue {} failed.", Constants.PROCESSED_QUEUE_NAME);
        }
        queue = new Queue(Constants.QUERY_EVENT_STATUS_RESPONSE_QUEUE_NAME, true);	// durable queue
        declaredQueue = amqpAdmin.declareQueue(queue);
        if (declaredQueue == null) {
            log.error("Declare queue {} failed.", Constants.QUERY_EVENT_STATUS_RESPONSE_QUEUE_NAME);
        }

        // 配置 exchange，用来 查询 event status
        boolean autoDelete = false;
        boolean durable = true;
        Exchange exchange = new FanoutExchange(Constants.EXCHANGE_QUERY_STATUS, durable, autoDelete);
        amqpAdmin.declareExchange(exchange);

        // 声明交换器，发送可靠消息用
        Exchange reliableEventExchange = new TopicExchange(Constants.EXCHANGE_RELIABLE_EVENT);
        this.amqpAdmin.declareExchange(reliableEventExchange);

        // 对于每种可靠事件类型,绑定队列到交换机
        for(ReliableEventType reliableEventType : ReliableEventType.values()) {
            // 声明对应的队列并绑定取交换器，routeKey及队列名称为事件枚举和名称。
        	String eventTypeName = reliableEventType.name().toLowerCase();
        	String queueName = Constants.RELIABLE_EVENT_QUEUE_NAME_PREFIX + eventTypeName;
            queue = new Queue(queueName, true);
            this.amqpAdmin.declareQueue(queue);
            
            String routingKeyPattern = eventTypeName +".*";
            Binding binding = new Binding(queueName, Binding.DestinationType.QUEUE, 
            		Constants.EXCHANGE_RELIABLE_EVENT, routingKeyPattern, null);
            this.amqpAdmin.declareBinding(binding);
        }
    }
}
