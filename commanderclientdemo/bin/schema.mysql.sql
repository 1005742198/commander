
/* 测试业务用表 */

drop table if exists orders;
CREATE TABLE orders
(
	Id INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键Id',
	ItemId BIGINT NOT NULL COMMENT '商品id',
	Amount TINYINT NOT NULL COMMENT '商品数量',
	PRIMARY KEY (id),
	UNIQUE uq_orders_id(id)
);

