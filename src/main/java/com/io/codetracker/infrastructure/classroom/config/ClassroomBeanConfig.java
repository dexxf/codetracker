package com.io.codetracker.infrastructure.classroom.config;


import java.security.SecureRandom;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClassroomBeanConfig {

    @Bean
    public SecureRandom clSecureRandom() {
        return new SecureRandom();
    }

}
