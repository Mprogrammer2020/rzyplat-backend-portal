package com.rzyplat;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableMongoAuditing
@SpringBootApplication
public class RzyplatApplication {

	public static void main(String[] args) {
		SpringApplication.run(RzyplatApplication.class, args);
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

    @Bean
    WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
				.allowedMethods("OPTIONS","GET","POST","PUT","DELETE")
				.allowedOrigins("*");
			}
		};
	}
}
