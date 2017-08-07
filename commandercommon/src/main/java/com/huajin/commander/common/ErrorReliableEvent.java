package com.huajin.commander.common;

/**
 * 处理失败的可靠事件，包括相关错误。
 * 
 * @author bo.yang
 * @date 2017年7月7日 下午1:26:33
 */
public class ErrorReliableEvent {
	private ReliableEvent reliableEvent;
	private Exception cause;
	
	public ReliableEvent getReliableEvent() {
		return reliableEvent;
	}
	public void setReliableEvent(ReliableEvent reliableEvent) {
		this.reliableEvent = reliableEvent;
	}
	public Exception getCause() {
		return cause;
	}
	public void setCause(Exception cause) {
		this.cause = cause;
	}
	public ErrorReliableEvent(ReliableEvent reliableEvent, Exception cause) {
		super();
		this.reliableEvent = reliableEvent;
		this.cause = cause;
	}
	
	@Override
	public String toString() {
		return "ErrorReliableEvent [reliableEvent=" + reliableEvent + ", cause=" + cause + "]";
	}
}
