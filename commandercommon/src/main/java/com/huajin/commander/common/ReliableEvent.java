package com.huajin.commander.common;

import java.io.Serializable;
import java.util.Date;

/**
 * 可靠事件
 * 
 * @author bo.yang
 * 
 */
public class ReliableEvent implements Serializable {
	
	private static final long serialVersionUID = -4705738481012090877L;
	
	private Long eventId;
	private String typeName;	// 对应枚举 ReliableEventType 的字面量，如 : TimelineEvent
	private Integer typeId;		// 对应枚举 ReliableEventType 的int值
	private ReliableEventStatus status;
	
	// 消息体对象
	private Object msgBody;
	
	// 事件来源，存放 appName、IP等标识信息
	private String source;
	
	// 创建时间
	private Date createTime;
	
	public Long getEventId() {
		return eventId;
	}
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
	
	public ReliableEventStatus getStatus() {
		return status;
	}
	public void setStatus(ReliableEventStatus status) {
		this.status = status;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Object getMsgBody() {
		return msgBody;
	}
	public void setMsgBody(Object msgBody) {
		this.msgBody = msgBody;
	}
	@Override
	public String toString() {
		return "ReliableEvent [eventId=" + eventId + ", typeName=" + typeName + ", status=" + status + ", msgBody=" + msgBody
				+ ", source=" + source + ", createTime=" + createTime + "]";
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String type) {
		this.typeName = type;
	}
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
}
