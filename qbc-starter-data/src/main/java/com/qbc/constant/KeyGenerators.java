package com.qbc.constant;

/**
 * Redis Key 生成器名称
 *
 * @author Ma
 */
public interface KeyGenerators {

	/**
	 * 根据反射的包名、类名、方法名和参数生成Redis的Key
	 */
	String REFLECT_KEY = "reflectKey";

}
