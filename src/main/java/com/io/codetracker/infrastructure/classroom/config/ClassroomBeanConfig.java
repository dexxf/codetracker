package com.io.codetracker.infrastructure.classroom.config;


import java.security.SecureRandom;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.io.codetracker.domain.classroom.factory.ClassroomFactory;
import com.io.codetracker.domain.classroom.repository.ClassroomUserDomainPort;
import com.io.codetracker.domain.classroom.service.ClassroomCreationService;

@Configuration
public class ClassroomBeanConfig {

    @Bean
    public SecureRandom clSecureRandom() {
        return new SecureRandom();
    }

    @Bean
    public ClassroomCreationService classroomCreateService(ClassroomFactory factory, ClassroomUserDomainPort classroomUserDomainPort) {
        return new ClassroomCreationService(factory, classroomUserDomainPort);
    }
    
}
