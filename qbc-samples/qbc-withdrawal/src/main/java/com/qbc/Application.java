package com.qbc;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.qbc.core.biz.CodeGeneratorBIZ;
import com.qbc.core.biz.DatabaseInfoBIZ;
import com.qbc.core.biz.DatabaseInfoBVO;
import com.qbc.core.utils.QbcStringUtils;

@SpringBootApplication
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
			DatabaseInfoBVO databaseInfoBVO = databaseInfoBIZ.getDatabaseInfoBVO();
			databaseInfoBVO.getTableInfos().forEach(tableInfo -> {
				Map<String, Object> param = new HashMap<>();
				param.put("tableInfo", tableInfo);
				Path path = Paths.get("src/main/java", QbcStringUtils.packageNameToPathName("com.qbc.dao"),
						tableInfo.getClassName() + "DVO.java");
				codeGeneratorBIZ.generate("DVO", param, path.toFile());
			});
		};
	}

}
