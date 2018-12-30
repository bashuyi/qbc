package com.qbc.utils.core;

import cn.izern.sequence.Sequence;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SnowflakeUtils {

	private Sequence sequence = new Sequence();

	public Long nextId() {
		return sequence.nextId();
	}

}
