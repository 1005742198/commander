package com.huajin.commander.client.repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huajin.commander.client.domain.LocalEventEntity;
import com.huajin.commander.common.ReliableEvent;
import com.huajin.commander.common.ReliableEventStatus;

/**
 * DAO 工具类
 * 
 * @author bo.yang
 */
public class RepositoryUtils {
	
	/**
	 * 从 ReliableEvent 对象生成实体对象 LocalEventEntity.
	 * @param reliableEvent 可靠事件
	 * @return 实体对象 LocalEventEntity
	 */
	public static LocalEventEntity getEntityFromReliableEvent(ReliableEvent reliableEvent) {
		LocalEventEntity localEventEntity = new LocalEventEntity();
		localEventEntity.setCreateTime(reliableEvent.getCreateTime());
		localEventEntity.setEventType(reliableEvent.getTypeId());
		localEventEntity.setName(reliableEvent.getTypeName());
		localEventEntity.setEventId(reliableEvent.getEventId());
		Object msgBody = reliableEvent.getMsgBody();
		if (msgBody != null) {
			String jsonString = JSON.toJSONString(msgBody);
			localEventEntity.setMsgBody(jsonString);
		}
		localEventEntity.setSource(reliableEvent.getSource());
		localEventEntity.setStatus(reliableEvent.getStatus().value());
		return localEventEntity;
	}
	
	/**
	 * 从实体对象生成一个可靠消息对象。
	 * @param eventEntity 本地可靠事件实体
	 * @return 可靠消息对象
	 */
	public static ReliableEvent getReliableEventFromEntity(LocalEventEntity eventEntity) {
		ReliableEvent reliableEvent = new ReliableEvent();
		reliableEvent.setCreateTime(eventEntity.getCreateTime());
		reliableEvent.setEventId(eventEntity.getEventId());
		Object params = JSONObject.parse(eventEntity.getMsgBody());
		reliableEvent.setMsgBody(params);
		reliableEvent.setSource(eventEntity.getSource());
		reliableEvent.setStatus(ReliableEventStatus.valueOf(eventEntity.getStatus()));
		reliableEvent.setTypeName(eventEntity.getName());
		return reliableEvent;
	}
}
