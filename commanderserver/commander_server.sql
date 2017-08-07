drop table if exists global_events;
CREATE TABLE global_events
(
	`Id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键Id',
	`EventId` BIGINT NOT NULL COMMENT '事件全局唯一id',
	`EventType` TINYINT NOT NULL COMMENT '事件类型',
	`Name` VARCHAR(50) NOT NULL COMMENT '事件名称',
	`Status` TINYINT NOT NULL COMMENT '事件状态',
	`MsgBody` TEXT COMMENT '事件消息体',
	`Source` VARCHAR(50) NOT NULL COMMENT '事件来源',
	`LocalCreateTime` DATETIME NOT NULL COMMENT '事件在本地服务中创建的时间',
	`CreateTime` DATETIME NOT NULL COMMENT '在事件管理器中创建的时间',
	`UpdateTime` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	PRIMARY KEY (id),
	UNIQUE uq_global_events_id(id),
	UNIQUE uq_global_events_eventid(EventId)
);

drop table if exists processed_events;
CREATE TABLE processed_events
(
	`Id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键Id',
	`EventId` BIGINT NOT NULL COMMENT '事件全局唯一id',
	`EventType` TINYINT NOT NULL COMMENT '事件类型',
	`Name` VARCHAR(50) NOT NULL COMMENT '事件名称',
	`Status` TINYINT NOT NULL COMMENT '事件状态',
	`MsgBody` TEXT COMMENT '事件消息体',
	`Source` VARCHAR(50) NOT NULL COMMENT '事件来源',
	`LocalCreateTime` DATETIME NOT NULL COMMENT '事件在本地服务中创建的时间',
	`CreateTime` DATETIME NOT NULL COMMENT '在事件管理器中创建的时间',
	`UpdateTime` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	PRIMARY KEY (id),
	UNIQUE uq_processed_events_id(id),
	UNIQUE uq_processed_events_eventid(EventId)
);
