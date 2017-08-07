package com.huajin.commander.client.domain;

import java.util.Date;

/**
 * 本地“可靠事件”实体
 * 
 * @author bo.yang
 */
//@Entity
//@Table(name="event_local")
public class LocalEventEntity extends BaseEntity {

//	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
//	@Column(name="eventid")
	private Long eventId;
	
//	@Column(name="eventtype")
	private Integer eventType;
	
	private String name;
	
	private Integer status;
	
	private String msgBody;
	
	private String source;
	
//	@Column(name="createtime")
	private Date createTime;
	
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
	public String getMsgBody() {
		return msgBody;
	}
	public void setMsgBody(String msgBody) {
		this.msgBody = msgBody;
	}
}
