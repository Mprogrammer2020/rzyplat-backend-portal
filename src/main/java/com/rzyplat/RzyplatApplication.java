package com.rzyplat;

import java.util.TimeZone;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.rzyplat.constant.Constants;

@EnableMongoAuditing
@SpringBootApplication
public class RzyplatApplication {

	public static void main(String[] args) {
		SpringApplication.run(RzyplatApplication.class, args);
		TimeZone.setDefault(TimeZone.getTimeZone(Constants.TZ_UTC));
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
    
    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
