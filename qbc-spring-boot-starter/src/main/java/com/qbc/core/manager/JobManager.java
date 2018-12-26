package com.qbc.core.manager;

import java.util.Date;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import lombok.SneakyThrows;

@Component
public class JobManager {

	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;

	@SneakyThrows
	public Date addJob(JobDTO jobDTO) {
		@SuppressWarnings("unchecked")
		Class<Job> jobClass = (Class<Job>) Class.forName(jobDTO.getJobClassName());
		Scheduler scheduled = schedulerFactoryBean.getScheduler();
		JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobDTO.getJobName(), jobDTO.getJobGroupName())
				.build();
		Trigger trigger = TriggerBuilder.newTrigger()
				.withIdentity(jobDTO.getTriggerName(), jobDTO.getTriggerGroupName())
				.withSchedule(CronScheduleBuilder.cronSchedule(jobDTO.getCronExpression())).build();
		return scheduled.scheduleJob(jobDetail, trigger);
	}

}
