package com.s4r.ghorbari.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.s4r.ghorbari")
@EnableJpaRepositories(basePackages = "com.s4r.ghorbari.core.repository")
@EntityScan(basePackages = "com.s4r.ghorbari.core.entity")
public class GhorbariApplication {

	public static void main(String[] args) {
		SpringApplication.run(GhorbariApplication.class, args);
	}
}
