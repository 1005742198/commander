package com.huajin.commander.manager.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * “可靠事件” 实体
 * 
 * @author bo.yang
 */
@Entity
@Table(name="global_events")
public class GlobalEventEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="eventid")
	private Long eventId;
	
	@Column(name="eventtype")
	private Integer eventType;
	
	private String name;	// 事件类型名，对应 ReliableEventType 枚举字面量
	
	private Integer status;
	
	private String msgBody;
	
	private String source;
	
	@Column(name="createtime")
	private Date createTime;
	
	@Column(name="localcreatetime")
	private Date localCreateTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getEventType() {
		return eventType;
	}
	public void setEventType(Integer eventType) {
		this.eventType = eventType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
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
	public Long getEventId() {
		return eventId;
	}
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
	public Date getLocalCreateTime() {
		return localCreateTime;
	}
	public void setLocalCreateTime(Date localCreateTime) {
		this.localCreateTime = localCreateTime;
	}
	public String getMsgBody() {
		return msgBody;
	}
	public void setMsgBody(String msgBody) {
		this.msgBody = msgBody;
	}
}
