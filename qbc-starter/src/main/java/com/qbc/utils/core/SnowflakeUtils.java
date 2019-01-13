package com.qbc.utils.core;

import cn.izern.sequence.Sequence;
import lombok.experimental.UtilityClass;

/**
 * 分布式唯一ID工具类
 *
 * @author Ma
 */
@UtilityClass
public class SnowflakeUtils {

	private Sequence sequence = new Sequence();

	/**
	 * 生成一个分布式唯一ID
	 * 
	 * @return
	 */
	public Long nextId() {
		return sequence.nextId();
	}

	public String nextString() {
		return Long.toString(sequence.nextId(), Character.MAX_RADIX);
	}

}
