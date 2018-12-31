package com.qbc.biz.core;

import java.util.Date;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.SneakyThrows;

@Component
public class JobBIZ {

	@Autowired
	private Scheduler scheduler;

	@SneakyThrows
	@SuppressWarnings("unchecked")
	public Date addJob(JobBVO jobBVO) {
		Class<Job> jobClass = (Class<Job>) Class.forName(jobBVO.getJobClassName());
		JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobBVO.getJobName(), jobBVO.getJobGroupName())
				.build();
		Trigger trigger = TriggerBuilder.newTrigger()
				.withIdentity(jobBVO.getTriggerName(), jobBVO.getTriggerGroupName())
				.withSchedule(CronScheduleBuilder.cronSchedule(jobBVO.getCronExpression())).build();
		return scheduler.scheduleJob(jobDetail, trigger);
	}

	@SneakyThrows
	public Date updateJob(JobBVO jobBVO) {
		TriggerKey triggerKey = new TriggerKey(jobBVO.getTriggerName(), jobBVO.getTriggerGroupName());
		Trigger trigger = TriggerBuilder.newTrigger()
				.withIdentity(jobBVO.getTriggerName(), jobBVO.getTriggerGroupName())
				.withSchedule(CronScheduleBuilder.cronSchedule(jobBVO.getCronExpression())).build();
		return scheduler.rescheduleJob(triggerKey, trigger);
	}

	@SneakyThrows
	public void pauseAllJob() {
		scheduler.pauseAll();
	}

}
