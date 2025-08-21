package ru.darkt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication(exclude = {org.springdoc.core.configuration.SpringDocHateoasConfiguration.class})
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class StepByStep {
    public static void main(String[] args) {
        SpringApplication.run(StepByStep.class, args);
    }
}