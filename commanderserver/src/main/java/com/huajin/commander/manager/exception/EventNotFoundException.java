package com.huajin.commander.manager.exception;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 当用 eventId 找不到对应的可靠事件时抛出。
 * 
 * @author bo.yang
 */
public class EventNotFoundException extends ReliableEventException {

	private static final long serialVersionUID = -8176989100867041002L;

	public EventNotFoundException() {
	}

	public EventNotFoundException(Long eventId) {
		super(String.format("No event entity exists for eventId '%d'.", eventId));
	}
	
	public EventNotFoundException(List<Long> eventIdList) {
		super(String.format("No event entity exists for eventId '%d'.", 
				eventIdList.stream()
				.map(id->""+id)
				.collect(Collectors.joining(","))));
	}
	
	public EventNotFoundException(String message) {
		super(message);
	}

	public EventNotFoundException(Throwable cause) {
		super(cause);
	}

	public EventNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public EventNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
