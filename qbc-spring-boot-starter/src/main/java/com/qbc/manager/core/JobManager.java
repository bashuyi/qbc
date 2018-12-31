package com.qbc.manager.core;

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
public class JobManager {

	@Autowired
	private Scheduler scheduler;

	@SneakyThrows
	@SuppressWarnings("unchecked")
	public Date addJob(JobDTO jobDTO) {
		Class<Job> jobClass = (Class<Job>) Class.forName(jobDTO.getJobClassName());
		JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobDTO.getJobName(), jobDTO.getJobGroupName())
				.build();
		Trigger trigger = TriggerBuilder.newTrigger()
				.withIdentity(jobDTO.getTriggerName(), jobDTO.getTriggerGroupName())
				.withSchedule(CronScheduleBuilder.cronSchedule(jobDTO.getCronExpression())).build();
		return scheduler.scheduleJob(jobDetail, trigger);
	}

	@SneakyThrows
	public Date updateJob(JobDTO jobDTO) {
		TriggerKey triggerKey = new TriggerKey(jobDTO.getTriggerName(), jobDTO.getTriggerGroupName());
		Trigger trigger = TriggerBuilder.newTrigger()
				.withIdentity(jobDTO.getTriggerName(), jobDTO.getTriggerGroupName())
				.withSchedule(CronScheduleBuilder.cronSchedule(jobDTO.getCronExpression())).build();
		return scheduler.rescheduleJob(triggerKey, trigger);
	}

	@SneakyThrows
	public void pauseAllJob() {
		scheduler.pauseAll();
	}

}
