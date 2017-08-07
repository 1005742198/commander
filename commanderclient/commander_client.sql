drop table if exists event_local;
CREATE TABLE event_local
(
	`Id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键Id',
	`EventId` BIGINT NOT NULL COMMENT '事件全局唯一id',
	`EventType` TINYINT NOT NULL COMMENT '事件类型',
	`Name` VARCHAR(50) NOT NULL COMMENT '事件名称',
	`Status` TINYINT NOT NULL COMMENT '事件状态',
	`MsgBody` TEXT COMMENT '事件消息体',
	`Source` VARCHAR(50) NOT NULL COMMENT '事件来源',
	`CreateTime` DATETIME NOT NULL COMMENT '创建时间',
	`UpdateTime` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	PRIMARY KEY (id),
	UNIQUE uq_local_events_id(id),
	UNIQUE uq_local_events_eventid(EventId)
) COMMENT="可靠事件本地记录表";

drop table if exists event_local_archive;
CREATE TABLE event_local_archive
(
	`Id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键Id',
	`EventId` BIGINT NOT NULL COMMENT '事件全局唯一id',
	`EventType` TINYINT NOT NULL COMMENT '事件类型',
	`Name` VARCHAR(50) NOT NULL COMMENT '事件名称',
	`Status` TINYINT NOT NULL COMMENT '事件状态',
	`MsgBody` TEXT COMMENT '事件消息体',
	`Source` VARCHAR(50) NOT NULL COMMENT '事件来源',
	`CreateTime` DATETIME NOT NULL COMMENT '创建时间',
	`UpdateTime` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	PRIMARY KEY (id),
	UNIQUE uq_archive_events_id(id),
	UNIQUE uq_archive_events_eventid(EventId)
) COMMENT="可靠事件归档表，归档已经被消费或者已取消的本地可靠事件";
