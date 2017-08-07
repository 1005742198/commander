package com.huajin.commander.client;

/**
 * 可靠事件管理器相关异常基础类。
 * 
 * @author bo.yang
 */
public class ReliableEventManagerException extends Exception {

	private static final long serialVersionUID = -2206090200371726391L;

	public ReliableEventManagerException() {
	}

	public ReliableEventManagerException(String message) {
		super(message);
	}

	public ReliableEventManagerException(Throwable cause) {
		super(cause);
	}

	public ReliableEventManagerException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReliableEventManagerException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
