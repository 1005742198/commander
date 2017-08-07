package com.huajin.commander.client;

import java.util.List;

import com.huajin.commander.common.ReliableEvent;
import com.huajin.commander.common.ReliableEventType;

/**
 * 可靠事件管理器的客户端API。
 * 
 * <p>主要功能是
 * <ol>
 * <li> 记录事件到本地事件表: {@link #createLocalEventForEntity(Object)}
 * <li> 通知事件管理器创建可靠事件: {@link #createRemoteEvent(ReliableEvent)}
 * <li> 确认提交事件: {@link #confirmSend()}
 * <li> 取消事件发送: {@link #cancelSend()}
 * </ol>
 * @author bo.yang
 */
public interface ReliableEventManagerClient {
	
	/**
	 * 创建可靠事件。
	 * 
	 * <p>
	 * 记录事件到本地事件表并调用远程的事件管理器创建该事件，
	 * 为发布“实体变动”事件做好准备，
	 * 实际发布动作的时间要在“所属事务”提交后才会发生，如果事务回滚则会自动取消发布。
	 * 
	 * <p>支持重复调用，对于相同的对象会被合并。
	 * 
	 * <p>TODO: 支持嵌套事务
	 * 
	 * @param msgBody 消息体数组，一个消息体会创建一个可靠事件。
	 * 
	 * <b>需要有主键，可以是id属性</b>
	 */
	List<ReliableEvent> createEvents(ReliableEventType eventType, Object... msgBody);
	
	/**
	 * 向远程的事件管理器"确认"指定事件的发送。
	 */
	void confirmSend();
	
	/**
	 * 取消当前线程创建的可靠事件。
	 * <p>会向远程的事件管理器"取消"当前线程最新创建的可靠事件。
	 */
	public void cancelSend();
	
}
