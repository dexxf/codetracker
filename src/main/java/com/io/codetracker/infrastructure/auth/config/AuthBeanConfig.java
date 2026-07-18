package com.io.codetracker.infrastructure.auth.config;

import com.io.codetracker.domain.auth.service.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AuthBeanConfig {

    @Bean
    public PasswordService passwordService(@Qualifier("passwordEncoder") PasswordHasher hasher) {
        return new PasswordService(hasher);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
