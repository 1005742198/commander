package com.huajin.commander.client.domain;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 实体基础类
 * <p>实现一些实体需要的功能方法。
 * 
 * @author bo.yang
 *
 */
public class BaseEntity {

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
