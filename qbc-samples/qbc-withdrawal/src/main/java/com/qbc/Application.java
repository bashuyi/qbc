package com.qbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.qbc.core.biz.CodeGeneratorManager;
import com.qbc.core.biz.DatabaseInfoDTO;
import com.qbc.core.biz.DatabaseInfoManager;
import com.slyak.spring.jpa.GenericJpaRepositoryFactoryBean;
import com.slyak.spring.jpa.GenericJpaRepositoryImpl;

/**
 * 项目启动类
 *
 * @author Ma
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.qbc", repositoryBaseClass = GenericJpaRepositoryImpl.class, repositoryFactoryBeanClass = GenericJpaRepositoryFactoryBean.class)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Autowired
	private DatabaseInfoManager databaseInfoManager;

	@Autowired
	private CodeGeneratorManager codeGeneratorManager;

	@Bean
	ApplicationRunner applicationRunner() {
		return args -> {
			DatabaseInfoDTO databaseInfoDTO = databaseInfoManager.getDatabaseInfoBVO("tx");
			codeGeneratorManager.generateAll("DO", "com.qbc.dao", databaseInfoDTO);
			codeGeneratorManager.generateAll("DAO", "com.qbc.dao", databaseInfoDTO);
		};
	}

}
