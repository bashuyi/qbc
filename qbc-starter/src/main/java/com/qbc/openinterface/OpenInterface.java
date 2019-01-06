package com.qbc.openinterface;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

/**
 * 开放接口：直接封装 Service 方法暴露成 RPC 接口。
 *
 * @author Ma
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface OpenInterface {

	@AliasFor(annotation = Component.class)
	String value() default "";

}
