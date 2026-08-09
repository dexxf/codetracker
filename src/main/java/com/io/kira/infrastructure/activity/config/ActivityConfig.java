package com.io.kira.infrastructure.activity.config;

import com.io.kira.domain.activity.factory.ActivityFactory;
import com.io.kira.domain.activity.repository.ActivityDomainRepository;
import com.io.kira.domain.activity.repository.ActivityUserDomainPort;
import com.io.kira.domain.activity.service.ActivityCreationService;
import com.io.kira.domain.activity.service.UpdateActivityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActivityConfig {

    @Bean
    public ActivityCreationService activityCreationService(ActivityDomainRepository activityDomainRepository, ActivityFactory activityFactory, ActivityUserDomainPort activityUserDomainPort) {
        return new ActivityCreationService(activityDomainRepository,activityFactory, activityUserDomainPort);
    }

    @Bean
    public UpdateActivityService validateEditActivityService(ActivityDomainRepository activityDomainRepository) {
        return new UpdateActivityService(activityDomainRepository);
    }
}
