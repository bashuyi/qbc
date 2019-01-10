package com.qbc.api;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * API操作，用于指定暴露给外部访问的方法名并提供方法的描述。
 *
 * @author Ma
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiOperation {

	/**
	 * API操作名，默认为方法名
	 */
	String name() default "";

	/**
	 * API操作描述
	 */
	String description() default "";

}
