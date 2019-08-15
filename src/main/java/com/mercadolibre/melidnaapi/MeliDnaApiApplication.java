package com.mercadolibre.melidnaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class MeliDnaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeliDnaApiApplication.class, args);
	}

}
