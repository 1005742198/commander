drop table event_local if exists;
CREATE TABLE event_local
(
	Id BIGINT NOT NULL generated by default as identity,
	EventId BIGINT NOT NULL COMMENT '事件全局唯一id',
	EventType TINYINT NOT NULL COMMENT '事件类型',
	Name VARCHAR(50) NOT NULL COMMENT '事件名称',
	Status TINYINT NOT NULL COMMENT '事件状态',
	MsgBody TEXT COMMENT '事件消息体',
	Source VARCHAR(50) NOT NULL COMMENT '事件来源',
	CreateTime DATETIME NOT NULL COMMENT '创建时间',
	PRIMARY KEY (id),
	UNIQUE uq_local_events_id(id),
	UNIQUE uq_local_events_eventid(EventId)
);

drop table event_local_archive if exists;
CREATE TABLE event_local_archive
(
	Id BIGINT NOT NULL generated by default as identity,
	EventId BIGINT NOT NULL COMMENT '事件全局唯一id',
	EventType TINYINT NOT NULL COMMENT '事件类型',
	Name VARCHAR(50) NOT NULL COMMENT '事件名称',
	Status TINYINT NOT NULL COMMENT '事件状态',
	MsgBody TEXT COMMENT '事件消息体',
	Source VARCHAR(50) NOT NULL COMMENT '事件来源',
	CreateTime DATETIME NOT NULL COMMENT '创建时间',
	PRIMARY KEY (id),
	UNIQUE uq_archive_events_id(id),
	UNIQUE uq_archive_events_eventid(EventId)
);


