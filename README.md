# Commander

Commander 项目包含多个子项目，目的是为微服务架构提供分布式事务支持。

* 子项目 commanderserver 提供“事件管理器”服务，是“可靠事件模式”的一个组件。
* 子项目 commanderclient “可靠事件模式”中客户端使用的库。
* 子项目 commanderclientdemo 使用“可靠事件模式”客户端的示例项目，有两个 profile 可以使用，分别是 demo1和demo2.

# 主要功能

## 支持一个事务中发起多个实体变更事件

客户端批量将变更的实体对象发送给“事件管理器”，但是事件管理器会逐个将“实体对象变更”发送到消息队列。

## 可靠事件功能使用方法

1、在 buidl.gradle 中加入依赖，可以参考 "commanderclientdemo" 项目

	compile ("com.huajin.commander.client:commander-client:0.0.1-RELEASE")

2、配置 DataSource

确认项目数据库中已经创建好表“event_local 和 event_local_archive”，在项目中配置好数据库对应的 DataSource。

3、自动拦截 @Transactional 方法

Commander Client 模块会自动配置Spring AOP，拦截带有 @Transactional 注解的方法，用于添加 Transaction Resource Listener.

4、在事务中创建事件

调用 ReliableEventManagerClient.createEventForEntity(Object... entities) 方法创建可靠事件，
目前只有“实体变化类型”事件，这些事件会自动在事务结束时提交或者回滚，并且有错误尝试机制保障可靠性即做到
“数据库操作和消息发送成为一个事务性操作，同时成功或者失败”。

5、消息队列

整个“可靠事件”组件系统使用4个RabbitMQ队列，如下：

* queue_reliable_event 可靠事件队列（使用 TOPIC 模式的 exchange）, 
  routing-key 命名方式：ReliableEventType.entityName 或者 ReliableEventType
* queue_reliable_event_processed 可靠事件成功处理队列（事件管理器能处理重复的通知）
* queue_reliable_event_query_status_xxx 可靠事件状态查询请求队列（每个服务会创建一个独占、服务退出自动删除的队列，绑定到 扇出 模式的 exchange）
* queue_reliable_event_query_status_response 可靠事件状态查询响应队列
* queue_reliable_event_error 处理时出现异常的消息，需要人工干预，重新处理

每个使用“可靠事件”组件Commander的项目，会自动创建一个 RabbitMQ 的 Listener，并自动响应发送到消息队列中的“可靠事件”状态查询消息，
该队列名为"queue_reliable_event_query_status"。

对应“实体变动事件”感兴趣的项目还需要侦听消息队列"queue_reliable_event"来获得实体变化消息。

# TODO

* 支持 mysql 数据库【完成】
* 支持一个事务中发起多个事件  【完成】
* 支持嵌套事务
* 发布 commander client 到 maven 仓库
* 支持 spring-boot 自动配置功能
* 添加 junit 单元测试和集成测试
