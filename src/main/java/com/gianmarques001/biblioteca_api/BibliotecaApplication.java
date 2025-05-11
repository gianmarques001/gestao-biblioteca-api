package com.gianmarques001.biblioteca_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync(proxyTargetClass = true)
public class BibliotecaApplication {
	public static void main(String[] args) {
		SpringApplication.run(BibliotecaApplication.class, args);
	}

}

