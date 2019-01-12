package com.qbc.api.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Service;

/**
 * API的注解。用于指定哪些服务暴露给外部访问，并且可以指定暴露给外部访问的服务名并提供服务的描述。
 *
 * @author Ma
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
public @interface Api {

	/** API名称，也是Bean名称 */
	@AliasFor(value = "value", annotation = Service.class)
	String name() default "";

	/** API表示名 */
	String displayName() default "";

	/** API描述 */
	String description() default "";

}
