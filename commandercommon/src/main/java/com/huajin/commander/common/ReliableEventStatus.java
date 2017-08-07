package com.huajin.commander.common;

/**
 * 可靠事件状态
 * 
 * @author bo.yang
 */
public enum ReliableEventStatus {
	Created(1),		// 已创建
	Sended(2),		// 已确认发送/发布
	Canceled(3),	// 已取消
	Processed(4)	// 已成功处理/执行
	;
	
	private int status;
	
	private ReliableEventStatus(int status) {
		this.status = status;
	}
	
	public int value() {
		return this.status;
	}

	/**
	 * 从 int 状态得到枚举对象。
	 * 
	 * @param intStatus 整数类型的状态值
	 * 
	 * @return 对应的枚举值; 如果没有相应的枚举就抛出 IllegalArgumentException。
	 */
	public static ReliableEventStatus valueOf(int intStatus ) {
		for (ReliableEventStatus status : ReliableEventStatus.values()) {
			if (status.value() == intStatus) {
				return status;
			}
		}
		throw new IllegalArgumentException("ReliableEventStatus 没有对应的枚举：" + intStatus);
	}
}
