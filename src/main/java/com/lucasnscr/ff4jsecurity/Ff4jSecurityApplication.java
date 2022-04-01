package com.lucasnscr.ff4jsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;

@SpringBootApplication(exclude = { ThymeleafAutoConfiguration.class, SecurityAutoConfiguration.class})
public class Ff4jSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(Ff4jSecurityApplication.class, args);
	}

}
