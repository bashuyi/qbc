package com.qbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.qbc.core.biz.DatabaseInfoBIZ;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Autowired
	private DatabaseInfoBIZ databaseInfoBIZ;

	@Bean
	ApplicationRunner applicationRunner() {
		return args -> {
			System.out.println(databaseInfoBIZ.getDatabaseInfoBVO());
		};
	}

}
