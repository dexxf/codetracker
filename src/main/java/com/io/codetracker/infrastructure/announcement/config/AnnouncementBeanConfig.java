package com.io.codetracker.infrastructure.announcement.config;

import org.apache.tika.Tika;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AnnouncementBeanConfig {

    @Bean
    public Tika tika() {
        return new Tika();
    }
}
