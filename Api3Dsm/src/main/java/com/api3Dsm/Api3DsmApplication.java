package com.api3Dsm;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Api3DsmApplication{
	
	
	public static void main(String[] args) {
		// inicio da feat-aderirservioco
		// SpringApplication.run(Api3DsmApplication.class, args);
		Map<String, Object> configuracao = new HashMap<>();
		
		configuracao.put("server.port", "8080");
		configuracao.put("spring.datasource.url", "jdbc:mysql://localhost:3306/CrudPro4Tech");
		configuracao.put("spring.datasource.username", "root");
		configuracao.put("spring.datasource.password", "root"); 
		configuracao.put("spring.jpa.show-sql", "true"); 
		configuracao.put("spring.jpa.hibernate.ddl-auto", "update");
		
		
	
		SpringApplication app = new SpringApplication(Api3DsmApplication.class);
		app.setDefaultProperties(configuracao);
		app.run(args);
	}

}
