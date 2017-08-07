package com.huajin.commander.common;

public class Constants {
	
	/**
	 * 交换器名，查询可靠事件状态的扇出交换器
	 */
	public static final String EXCHANGE_QUERY_STATUS = "exchange_reliable_event_query_status";
	
	/**
	 * 交换器名，发送可靠事件的交换器
	 */
	public static final String EXCHANGE_QUERY_STATUS_RESPONSE = "exchange_reliable_event_query_status_response";

	/**
	 * 交换器名，发送可靠事件的交换器
	 */
	public static final String EXCHANGE_RELIABLE_EVENT = "exchange_reliable_event";

	/**
	 * 收发可靠消息的队列名前缀
	 */
	public static final String RELIABLE_EVENT_QUEUE_NAME_PREFIX = "queue_reliable_event_";
	
	/**
	 * 查询可靠事件状态的队列名
	 */
	public static final String QUERY_EVENT_STATUS_QUEUE_NAME = "queue_reliable_event_query_status";

	/**
	 * 可靠事件状态查询的响应消息队列名
	 */
	public static final String QUERY_EVENT_STATUS_RESPONSE_QUEUE_NAME = "queue_reliable_event_query_status_response";

	/**
	 * 发送可靠事件已经成功处理的队列
	 */
	public static final String PROCESSED_QUEUE_NAME = "queue_reliable_event_processed";
	
	/**
	 * 可靠事件处理失败队列，供报警和后续处理用
	 */
	public static final String QUEUE_RELIABLE_EVENT_ERROR = "queue_reliable_event_error";
}
