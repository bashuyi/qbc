package com.qbc.config.core;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.slyak.spring.jpa.GenericJpaRepositoryFactoryBean;
import com.slyak.spring.jpa.GenericJpaRepositoryImpl;

@Configuration
@EnableJpaRepositories(basePackages = "${qbc.jpa.base-package:com.qbc}", repositoryBaseClass = GenericJpaRepositoryImpl.class, repositoryFactoryBeanClass = GenericJpaRepositoryFactoryBean.class)
public class JpaConfig {

}
