package com.huajin.commander.client.repository;

import com.huajin.commander.client.domain.LocalEventEntity;

/**
 * LocalEvent mybatis DAO.
 * 
 * @author bo.yang
 * @date 2017年6月30日 上午9:41:12
 */
public interface LocalEventDao {
	
	/**
	 * @param eventId 事件id
	 * @return 
	 * @author bo.yang
	 * @date 2017年6月30日 上午9:56:43
	 */
	LocalEventEntity findByEventId(Long eventId);
	
	/**
	 * 创建本地事件
	 * 
	 * @param localEvent
	 * @return 实体id
	 * @author bo.yang
	 * @date 2017年6月30日 上午9:56:27
	 */
	int insert(LocalEventEntity localEvent);

	/**
	 * 更新
	 * @param localEvent
	 * @return 更新条数
	 * @author bo.yang
	 * @date 2017年6月30日 上午11:12:29
	 */
	int update(LocalEventEntity localEvent);
}
