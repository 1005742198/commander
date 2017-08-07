package com.huajin.commander.client.mq;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.huajin.commander.common.Constants;
import com.huajin.commander.common.ReliableEventType;


/**
 * 声明queue和exchange.
 * 
 * @author bo.yang
 * @date 2017年6月29日 下午2:42:48
 */
@Component
public class QueueExchangeConfigure {

	@Autowired
	private AmqpAdmin amqpAdmin;
	
	@Autowired
	private QueueNameBean queueNameBean;
	
	private Logger log = LoggerFactory.getLogger(getClass());

	/**
	 * 配置队列、交换器(fan-out)、绑定。
	 */
	@PostConstruct
	public void configQueueAndExchange() {
		
		// configure exchange
		// 查询状态用的交换器
		Exchange exchangeQuery = new FanoutExchange(Constants.EXCHANGE_QUERY_STATUS, true, false);
		amqpAdmin.declareExchange(exchangeQuery);
		log.debug("Declared exchange-query-event-status '{}'", exchangeQuery.getName());
		
		// 发送查询响应用的交换器
		Exchange exchangeQueryResponse = new DirectExchange(Constants.EXCHANGE_QUERY_STATUS_RESPONSE, true, false);
		amqpAdmin.declareExchange(exchangeQueryResponse);
		log.debug("Declared exchange-response-of-query-status '{}'", exchangeQueryResponse.getName());
		
		// configure queue 查询状态用的队列，每个服务单独使用一个
		String queryStatusQueueName = queueNameBean.getQueryEventStatusQueueName();
		// durable:true, exclusive:true, auto-del:true
		Queue queueQueryStatus = new Queue(queryStatusQueueName, true, true, true);
		String declaredQueue = amqpAdmin.declareQueue(queueQueryStatus);
		log.debug("Declared query-event-status-queue '{}'", declaredQueue);
		
		// 声明响应队列
		Queue queueResponse = new Queue(Constants.QUERY_EVENT_STATUS_RESPONSE_QUEUE_NAME, true, false, false);
		String declaredResponseQueue = amqpAdmin.declareQueue(queueResponse);
		log.debug("Declared response-of-query-status-queue '{}'", declaredResponseQueue);

		// binding exchange and queue
	    Binding binding = BindingBuilder.bind(queueQueryStatus).to(exchangeQuery).with(queryStatusQueueName).noargs();
	    amqpAdmin.declareBinding(binding);
	    log.debug("Bind queue '{}' to exchange '{}'", declaredQueue, exchangeQuery.getName());
	    
	    binding = BindingBuilder.bind(queueResponse).to(exchangeQueryResponse).with(queueResponse.getName()).noargs();
	    amqpAdmin.declareBinding(binding);
	    log.debug("Bind queue '{}' to exchange '{}'", queueResponse.getName(), exchangeQueryResponse.getName());
	    
	    configReliableEventQueueAndBinding();
	}
	
	/**
	 * 配置“可靠事件”消息队列和绑定.
	 * 
	 * @author bo.yang
	 * @date 2017年6月23日 下午5:13:27
	 */
    private void configReliableEventQueueAndBinding(){
    	
        // 配置 exchange，用来 查询 event status
        boolean durable = true;

        // 声明交换器，发送可靠消息用
        Exchange reliableEventExchange = new TopicExchange(Constants.EXCHANGE_RELIABLE_EVENT);
        this.amqpAdmin.declareExchange(reliableEventExchange);
        
        log.debug("Declare reliable-event exchange '{}'", reliableEventExchange.getName());

        // 对于每种可靠事件类型, 绑定队列到交换器
        for(ReliableEventType reliableEventType : ReliableEventType.values()) {
            // 声明对应的队列并绑定取交换器，routeKey及队列名称为事件枚举和名称。
        	String eventTypeName = reliableEventType.name().toLowerCase();
        	String queueName = Constants.RELIABLE_EVENT_QUEUE_NAME_PREFIX + eventTypeName;
            Queue queue = new Queue(queueName, durable);
            String declaredQueueName = this.amqpAdmin.declareQueue(queue);
            log.debug("Declare reliable-event-queue '{}'", declaredQueueName);
            
            String routingKeyPattern = eventTypeName +".*";
            Binding binding = new Binding(queueName, Binding.DestinationType.QUEUE, 
            		Constants.EXCHANGE_RELIABLE_EVENT, routingKeyPattern, null);
            this.amqpAdmin.declareBinding(binding);
            log.debug("Bind queue '{}' to exchange '{}' with routing-key '{}'", declaredQueueName, Constants.EXCHANGE_RELIABLE_EVENT,
            		routingKeyPattern);
        }
    }
}
