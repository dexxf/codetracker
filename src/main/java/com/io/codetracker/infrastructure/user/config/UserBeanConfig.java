package com.io.codetracker.infrastructure.user.config;

import com.io.codetracker.domain.user.factory.UserFactory;
import com.io.codetracker.domain.user.service.UserCreationService;

import com.io.codetracker.domain.user.service.UserProfileUpdater;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserBeanConfig {

    @Bean
    public UserCreationService userCreationService (@Qualifier("defaultUserFactory") UserFactory factory) {
        return new UserCreationService(factory);
    }

    @Bean
    public UserProfileUpdater userProfileUpdater () {
        return new UserProfileUpdater();
    }

}
