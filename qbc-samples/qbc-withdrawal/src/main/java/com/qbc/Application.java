package com.qbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.qbc.biz.core.JobBVO;
import com.qbc.biz.core.JobBIZ;
import com.slyak.spring.jpa.GenericJpaRepositoryFactoryBean;
import com.slyak.spring.jpa.GenericJpaRepositoryImpl;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.qbc", repositoryBaseClass = GenericJpaRepositoryImpl.class, repositoryFactoryBeanClass = GenericJpaRepositoryFactoryBean.class)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Autowired
	private JobBIZ jobManager;

	@Bean
	ApplicationRunner applicationRunner() {
		return args -> {
			JobBVO jobDTO = new JobBVO();
			jobDTO.setJobName("StatusQueryJob");
			jobDTO.setJobGroupName("qbc");
			jobDTO.setTriggerName("StatusQueryJob");
			jobDTO.setTriggerGroupName("qbc");
			jobDTO.setJobClassName("com.qbc.job.StatusQueryJob");
			jobDTO.setCronExpression("*/5 * * * * ?");
			jobManager.addJob(jobDTO);
		};
	}

}
