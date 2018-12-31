package com.qbc.biz.core;

import lombok.Data;

@Data
public class JobDTO {

	private String jobName;

	private String jobGroupName;

	private String triggerName;

	private String triggerGroupName;

	private String jobClassName;

	private String cronExpression;

}
