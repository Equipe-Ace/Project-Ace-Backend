package com.api3Dsm;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.api3Dsm.domain.modelo.Usuario;
import com.api3Dsm.domain.repositorio.UsuarioRepositorio;

/* 
@SpringBootApplication
public class Api3DsmApplication implements CommandLineRunner{
	
	@Autowired
	public UsuarioRepositorio repositorio;
	
	
	public static void main(String[] args) {
		Map<String, Object> configuracao = new HashMap<>();
		
		configuracao.put("server.port", "8080");
		configuracao.put("spring.datasource.url", "jdbc:mysql://localhost:3306/pro4tech");
		configuracao.put("spring.datasource.username", "root");
		configuracao.put("spring.datasource.password", "fatec"); 
		configuracao.put("spring.jpa.show-sql", "true"); 
		configuracao.put("spring.jpa.hibernate.ddl-auto", "create");
		
		SpringApplication app = new SpringApplication(Api3DsmApplication.class);
		app.setDefaultProperties(configuracao);
		app.run(args);
	}

	@Override
	public void run(String... args) throws Exception {
		Usuario usuario = new Usuario();
		usuario.setEmail("admin@gmail.com");
		usuario.setSenha("admin");
		usuario.setCargo("administrador");
		repositorio.save(usuario);
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer()
	{
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedMethods("GET", "POST","PUT", "DELETE").allowedOrigins("http://localhost:3000");
			}
		};
	}
}

*/

@SpringBootApplication
public class Api3DsmApplication implements CommandLineRunner{

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public static void main(String[] args) {
		SpringApplication.run(Api3DsmApplication.class, args);
	}



	@Override
	public void run(String... args) throws Exception {
		
		Usuario user = new Usuario();
		user.setEmail("admin@gmail.com");
		user.setSenha(passwordEncoder.encode("123"));
		user.setCargo("ADMIN");
		usuarioRepositorio.save(user);

		Usuario userFin = new Usuario();
		userFin.setEmail("fin@gmail.com");
		userFin.setSenha(passwordEncoder.encode("123"));
		userFin.setCargo("FINANCEIRO");
		usuarioRepositorio.save(userFin);

		Usuario userCom = new Usuario();
		userCom.setEmail("com@gmail.com");
		userCom.setSenha(passwordEncoder.encode("123"));
		userCom.setCargo("COMERCIAL");
		usuarioRepositorio.save(userCom);
	}
}