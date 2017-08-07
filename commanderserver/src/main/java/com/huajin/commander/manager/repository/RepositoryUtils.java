package com.huajin.commander.manager.repository;

import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huajin.commander.common.ReliableEvent;
import com.huajin.commander.common.ReliableEventStatus;
import com.huajin.commander.manager.domain.GlobalEventEntity;

/**
 * 数据访问工具类
 * 
 * @author bo.yang
 */
public class RepositoryUtils {

	/**
	 * 从可靠事件 ReliableEvent 生成实体对象 GlobalEventEntity.
	 * 
	 * @param reliableEvent 可靠事件对象
	 * @return 实体对象 GlobalEventEntity
	 */
	public static GlobalEventEntity getEntityFromReliableEvent(ReliableEvent reliableEvent) {
		GlobalEventEntity globalEventEntity = new GlobalEventEntity();
		globalEventEntity.setCreateTime(new Date());
		globalEventEntity.setLocalCreateTime(reliableEvent.getCreateTime());
		globalEventEntity.setEventType(reliableEvent.getTypeId());
		globalEventEntity.setName(reliableEvent.getTypeName());
		globalEventEntity.setEventId(reliableEvent.getEventId());
		Object msgBody = reliableEvent.getMsgBody();
		if (msgBody != null) {
			String jsonString = JSON.toJSONString(msgBody);
			globalEventEntity.setMsgBody(jsonString);
		}
		globalEventEntity.setSource(reliableEvent.getSource());
		globalEventEntity.setStatus(reliableEvent.getStatus().value());
		return globalEventEntity;
	}

	/**
	 * 从全局事件实体得到一个"可靠事件"对象。
	 * 
	 * @param eventEntity 全局事件实体
	 * @return 可靠事件对象
	 */
	public static ReliableEvent getReliableEventFromEntity(GlobalEventEntity eventEntity) {
		ReliableEvent reliableEvent = new ReliableEvent();
		reliableEvent.setEventId(eventEntity.getEventId());
		reliableEvent.setTypeName(eventEntity.getName());
		reliableEvent.setTypeId(eventEntity.getEventType());
		reliableEvent.setCreateTime(eventEntity.getLocalCreateTime());
		Object msgBody = JSONObject.parse(eventEntity.getMsgBody());
		reliableEvent.setMsgBody(msgBody);
		reliableEvent.setSource(eventEntity.getSource());
		reliableEvent.setStatus(ReliableEventStatus.valueOf(eventEntity.getStatus()));
		return reliableEvent;
	}

}
