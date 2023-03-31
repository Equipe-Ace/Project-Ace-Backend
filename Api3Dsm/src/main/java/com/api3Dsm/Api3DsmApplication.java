package com.api3Dsm;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.api3Dsm.domain.modelo.Endereco;
import com.api3Dsm.domain.modelo.Parcela;
import com.api3Dsm.domain.modelo.Servico;
import com.api3Dsm.domain.modelo.Usuario;
import com.api3Dsm.domain.repositorio.UsuarioRepositorio;

@SpringBootApplication
public class Api3DsmApplication implements CommandLineRunner{
	
	@Autowired
	public UsuarioRepositorio repositorio;
	
	
	public static void main(String[] args) {
		Map<String, Object> configuracao = new HashMap<>();
		
		configuracao.put("server.port", "8080");
		configuracao.put("spring.datasource.url", "jdbc:mysql://localhost:3306/crudpro4tech");
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
		usuario.setNome("Andre");
		usuario.setCpf("123456789");
		
		Endereco end = new Endereco();
		end.setCep("12247090");
		end.setRua("rua fulano de tal");
		end.setBairro("bairro tal");
		end.setNumero(120);
		end.setComplemento(null);
		end.setCidade("são jose");
		end.setUF("SP");
		
		Parcela parcela = new Parcela();
		parcela.setDataCredito(null);
		parcela.setDataPagamento(null);
		parcela.setDataVencimento("05/12/2023");
		parcela.setValorParcela(50.00f);
		parcela.setValorPago(null);
		

	
		Servico servico = new Servico();
		servico.setPreco(500.00f);
		servico.getParcelas().add(parcela);


		usuario.setEndereco(end);
		usuario.getServicos().add(servico);
		
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
