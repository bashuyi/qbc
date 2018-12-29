package com.qbc.core.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Snowflake算法是带有时间戳的全局唯一ID生成算法。它有一套固定的ID格式，如下：
 *
 * <p>
 * 41位的时间序列（精确到毫秒，41位的长度可以使用69年） 10位的机器标识（10位的长度最多支持部署1024个节点）
 * 12位的Sequence序列号（12位的Sequence序列号支持每个节点每毫秒产生4096个ID序号）
 *
 * <p>
 * 结构如下(每部分用-分开):<br>
 * 0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 -
 * 000000000000 <br>
 * 优点是：整体上按照时间自增排序，且整个分布式系统内不会产生ID碰撞(由数据中心ID和机器ID作区分) <br>
 */
@Component
public class SnowflakeManager {

	@Autowired
	private SnowflakeProperties snowflakeProperties;

	/** workerId占用的位数5（表示只允许workId的范围为：0-1023） **/
	private final long workerIdBits = 5L;
	/** dataCenterId占用的位数：5 **/
	private final long dataCenterIdBits = 5L;
	/** 序列号占用的位数：12（表示只允许workId的范围为：0-4095） **/
	private final long sequenceBits = 12L;

	private final long workerIdShift = sequenceBits;
	private final long dataCenterIdShift = sequenceBits + workerIdBits;
	private final long timestampLeftShift = sequenceBits + workerIdBits + dataCenterIdBits;

	/** 用mask防止溢出:位与运算保证计算的结果范围始终是 0-4095 **/
	private final long sequenceMask = -1L ^ (-1L << sequenceBits);

	private long sequence = 0L;
	private long lastTimestamp = -1L;

	/**
	 * 获取ID
	 *
	 * @return
	 */
	public synchronized Long nextId() {
		long timestamp = this.timeGen();

		// 闰秒：如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
		if (timestamp < lastTimestamp) {
			long offset = lastTimestamp - timestamp;
			if (offset <= 5) {
				try {
					this.wait(offset << 1);
					timestamp = this.timeGen();
					if (timestamp < lastTimestamp) {
						throw new RuntimeException(String
								.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", offset));
					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			} else {
				throw new RuntimeException(
						String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", offset));
			}
		}

		// 解决跨毫秒生成ID序列号始终为偶数的缺陷:如果是同一时间生成的，则进行毫秒内序列
		if (lastTimestamp == timestamp) {
			// 通过位与运算保证计算的结果范围始终是 0-4095
			sequence = (sequence + 1) & sequenceMask;
			if (sequence == 0) {
				timestamp = this.tilNextMillis(lastTimestamp);
			}
		} else {
			// 时间戳改变，毫秒内序列重置
			sequence = 0L;
		}

		lastTimestamp = timestamp;

		/*
		 * 1.左移运算是为了将数值移动到对应的段(41、5、5，12那段因为本来就在最右，因此不用左移)
		 * 2.然后对每个左移后的值(la、lb、lc、sequence)做位或运算，是为了把各个短的数据合并起来，合并成一个二进制数
		 * 3.最后转换成10进制，就是最终生成的id
		 */
		return ((timestamp - snowflakeProperties.getStartTime()) << timestampLeftShift)
				| (snowflakeProperties.getDataCenterId() << dataCenterIdShift)
				| (snowflakeProperties.getWorkerId() << workerIdShift) | sequence;
	}

	/**
	 * 保证返回的毫秒数在参数之后(阻塞到下一个毫秒，直到获得新的时间戳)
	 *
	 * @param lastTimestamp
	 * @return
	 */
	private long tilNextMillis(long lastTimestamp) {
		long timestamp = this.timeGen();
		while (timestamp <= lastTimestamp) {
			timestamp = this.timeGen();
		}

		return timestamp;
	}

	/**
	 * 获得系统当前毫秒数
	 *
	 * @return timestamp
	 */
	private long timeGen() {
		return System.currentTimeMillis();
	}

}
