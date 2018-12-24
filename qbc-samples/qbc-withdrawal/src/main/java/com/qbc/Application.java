package com.qbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.qbc.core.biz.CodeGeneratorBIZ;
import com.qbc.core.biz.DatabaseInfoBIZ;
import com.qbc.core.biz.DatabaseInfoBVO;
import com.slyak.spring.jpa.GenericJpaRepositoryFactoryBean;
import com.slyak.spring.jpa.GenericJpaRepositoryImpl;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.qbc", repositoryBaseClass = GenericJpaRepositoryImpl.class, repositoryFactoryBeanClass = GenericJpaRepositoryFactoryBean.class)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Autowired
	private DatabaseInfoBIZ databaseInfoBIZ;

	@Autowired
	private CodeGeneratorBIZ codeGeneratorBIZ;

	@Bean
	ApplicationRunner applicationRunner() {
		return args -> {
			DatabaseInfoBVO databaseInfoBVO = databaseInfoBIZ.getDatabaseInfoBVO("tx");
			codeGeneratorBIZ.generateAll("DVO", "com.qbc.dao", databaseInfoBVO);
			codeGeneratorBIZ.generateAll("DAO", "com.qbc.dao", databaseInfoBVO);
		};
	}

}
