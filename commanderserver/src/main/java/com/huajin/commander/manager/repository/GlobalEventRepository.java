package com.huajin.commander.manager.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.huajin.commander.manager.domain.GlobalEventEntity;

/**
 * 全局事件DAO接口
 * 
 * @author bo.yang
 */
public interface GlobalEventRepository extends CrudRepository<GlobalEventEntity, Long> {
	
	List<GlobalEventEntity> findByStatusAndCreateTimeLessThan(int status, Date createTime);

	GlobalEventEntity findByEventId(Long eventId);
}
