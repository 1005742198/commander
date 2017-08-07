package com.huajin.commander.client.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 可靠事件注解.
 * <p>添加本注解的方法才会自动提交和回滚“可靠事件”。
 * 
 * @author bo.yang
 * @date 2017年7月3日 下午5:28:50
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface EnableReliableEvent {

}
