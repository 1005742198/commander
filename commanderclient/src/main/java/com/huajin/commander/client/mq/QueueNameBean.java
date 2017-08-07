package com.huajin.commander.client.mq;

import org.springframework.stereotype.Component;

import com.huajin.commander.common.Constants;

/**
 * 计算队列名称的 bean.
 * 
 * @author bo.yang
 */
@Component
public class QueueNameBean {

	private String queryEventStatusQueueName = MQUtils.randomQueueName(Constants.QUERY_EVENT_STATUS_QUEUE_NAME);

	public String getQueryEventStatusQueueName() {
		return queryEventStatusQueueName;
	}
	
}
