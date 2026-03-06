package com.io.codetracker.infrastructure.activity.config;

import com.io.codetracker.domain.activity.factory.ActivityFactory;
import com.io.codetracker.domain.activity.repository.ActivityDomainRepository;
import com.io.codetracker.domain.activity.repository.ActivityUserDomainPort;
import com.io.codetracker.domain.activity.service.ActivityCreationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActivityConfig {

    @Bean
    public ActivityCreationService activityCreationService(ActivityDomainRepository activityDomainRepository, ActivityFactory activityFactory, ActivityUserDomainPort activityUserDomainPort) {
        return new ActivityCreationService(activityDomainRepository,activityFactory, activityUserDomainPort);
    }
}
