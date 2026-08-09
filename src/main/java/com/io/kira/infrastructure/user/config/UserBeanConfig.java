package com.io.kira.infrastructure.user.config;


import com.io.kira.domain.user.service.UserProfileUpdater;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserBeanConfig {

    @Bean
    public UserProfileUpdater userProfileUpdater () {
        return new UserProfileUpdater();
    }

}
