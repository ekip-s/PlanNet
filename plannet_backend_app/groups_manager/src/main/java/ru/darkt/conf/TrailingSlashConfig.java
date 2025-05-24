package ru.darkt.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.UrlHandlerFilter;
import jakarta.servlet.*;

@Configuration
public class TrailingSlashConfig {

    @Bean
    public Filter trailingSlashFilter() {
        return UrlHandlerFilter
                .trailingSlashHandler("/**")
                .wrapRequest()
                .build();
    }
}