package com.huajin.commander.manager.exception;

/**
 * “可靠事件”相关的异常基础类，是 unchecked 类型的异常。
 * 
 * @author bo.yang
 */
public class ReliableEventException extends RuntimeException {

	private static final long serialVersionUID = -7186898081574119367L;

	public ReliableEventException() {
	}

	public ReliableEventException(String message) {
		super(message);
	}

	public ReliableEventException(Throwable cause) {
		super(cause);
	}

	public ReliableEventException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReliableEventException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
