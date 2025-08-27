package ru.darkt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication(exclude = {org.springdoc.core.configuration.SpringDocHateoasConfiguration.class})
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class PowerPlan {
    public static void main(String[] args) {
        SpringApplication.run(PowerPlan.class, args);
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }
}

