package com.huajin.commander.common;

/**
 * 可靠事件类型。
 * 
 * <p>
 * 用 ReliableEventType.value() 获取事件类型的int值.
 * 
 * @author bo.yang
 *
 */
public enum ReliableEventType {
	
	//时间线（动态）事件
	TimelineEvent(1)
	;
	
	private int type;	// 可靠事件类型序号

	private ReliableEventType(int typeId) {
		this.type = typeId;
	}

	/**
	 * @return ReliableEventType 的整数类型值
	 */
	public int value() {
		return this.type;
	}

	/**
	 * 从 int 类型得到枚举对象。
	 * 
	 * @param eventType 整数型的类型值
	 * 
	 * @return 对应的枚举值; 如果没有相应的枚举就抛出 IllegalArgumentException。
	 */
	public static ReliableEventType valueOf(int eventType) {
		for (ReliableEventType type : ReliableEventType.values()){
			if (type.value() == eventType) {
				return type;
			}
		}
		throw new IllegalArgumentException("ReliableEventType 没有对应的枚举：" + eventType);
	}

}
