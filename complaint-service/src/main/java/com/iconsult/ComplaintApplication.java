package com.iconsult;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableEurekaClient
public class ComplaintApplication {
    public static void main(String[] args) {
        SpringApplication.run(ComplaintApplication.class,args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}