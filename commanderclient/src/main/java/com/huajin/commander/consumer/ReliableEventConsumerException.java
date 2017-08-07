package com.huajin.commander.consumer;

/**
 * 可靠事件消费端相关异常基础类。
 * 
 * @author bo.yang
 */
public class ReliableEventConsumerException extends RuntimeException {

	private static final long serialVersionUID = -2206090200371726391L;

	public ReliableEventConsumerException() {
	}

	public ReliableEventConsumerException(String message) {
		super(message);
	}

	public ReliableEventConsumerException(Throwable cause) {
		super(cause);
	}

	public ReliableEventConsumerException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReliableEventConsumerException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
