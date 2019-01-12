package com.qbc.api.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * API参数
 *
 * @author Ma
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiParam {

	/** API参数名，默认为变量名。 */
	String name() default "";

	/** API参数表示名 */
	String displayName() default "";

	/** API参数描述 */
	String description() default "";

}
