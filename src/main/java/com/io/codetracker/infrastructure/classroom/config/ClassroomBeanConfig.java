package com.io.codetracker.infrastructure.classroom.config;

import java.security.SecureRandom;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.io.codetracker.domain.classroom.repository.ClassroomDomainRepository;
import com.io.codetracker.domain.classroom.repository.ClassroomSettingsDomainRepository;
import com.io.codetracker.domain.classroom.repository.ClassroomStudentDomainRepository;
import com.io.codetracker.domain.classroom.service.ClassroomJoinService;
import com.io.codetracker.domain.classroom.service.UpdateClassroomService;

@Configuration
public class ClassroomBeanConfig {

    @Bean
    public SecureRandom clSecureRandom() {
        return new SecureRandom();
    }

    @Bean
    public ClassroomJoinService classroomJoinService(
            ClassroomDomainRepository classroomDomainRepository,
            ClassroomSettingsDomainRepository classroomSettingsDomainRepository,
            ClassroomStudentDomainRepository classroomStudentDomainRepository
    ) {
        return new ClassroomJoinService(
                classroomDomainRepository,
                classroomSettingsDomainRepository,
                classroomStudentDomainRepository
        );
    }

    @Bean
    public UpdateClassroomService updateClassroomService(ClassroomDomainRepository classroomDomainRepository) {
        return new UpdateClassroomService(classroomDomainRepository);
    }
}
