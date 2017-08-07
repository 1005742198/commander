package com.huajin.commander.client.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.huajin.commander.client.domain.LocalEventEntity;
import com.huajin.commander.client.repository.LocalEventDao;
import com.huajin.commander.client.repository.RepositoryUtils;
import com.huajin.commander.common.Constants;
import com.huajin.commander.common.ReliableEvent;

/**
 * 可靠消息 Commander 客户端消息侦听器。
 * 
 * @author bo.yang
 */
@Component
public class MessageListener {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private LocalEventDao localEventRepository;
	
	@Autowired
	private AmqpTemplate amqpTempalte;
	
	@Autowired
	private QueueNameBean queueNameBean;
	
	/**
	 * 响应“可靠事件”状态查询消息。
	 * @param eventId 要查询的消息id
	 */
	@RabbitListener(queues="#{queueNameBean.queryEventStatusQueueName}")
	public void onQueryReliableEventStatus(Long eventId) {
		
		log.debug("Received query-status message(eventId={}) on queue '{}'.", 
				eventId, queueNameBean.getQueryEventStatusQueueName());
		
		LocalEventEntity eventEntity = localEventRepository.findByEventId(eventId);
		if (eventEntity == null) {
			log.debug("There is no event entity of eventId({})", eventId);
			return;
		}
		ReliableEvent reliableEvent = RepositoryUtils.getReliableEventFromEntity(eventEntity);
		String responseQueueName = Constants.QUERY_EVENT_STATUS_RESPONSE_QUEUE_NAME;
		amqpTempalte.convertAndSend(Constants.EXCHANGE_QUERY_STATUS_RESPONSE, responseQueueName, reliableEvent);
		
		log.debug("Sended response of query-status to queue '{}'. Entity is:\n{}", responseQueueName, eventEntity);
	}
	
}
