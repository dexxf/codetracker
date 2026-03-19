package com.io.codetracker.infrastructure.classroom.config;

import java.security.SecureRandom;

import com.io.codetracker.domain.classroom.factory.ClassroomStudentFactory;
import com.io.codetracker.domain.classroom.repository.ClassroomDomainRepository;
import com.io.codetracker.domain.classroom.repository.ClassroomSettingsDomainRepository;
import com.io.codetracker.domain.classroom.repository.ClassroomStudentDomainRepository;
import com.io.codetracker.domain.classroom.service.ClassroomJoinService;
import com.io.codetracker.domain.classroom.service.ClassroomStudentCreationService;
import com.io.codetracker.domain.classroom.service.UpdateClassroomService;
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

    @Bean
    public ClassroomStudentCreationService classroomStudentCreationService(ClassroomStudentFactory factory, ClassroomStudentDomainRepository repository) {
        return new ClassroomStudentCreationService(factory,repository);
    }

    @Bean
    public ClassroomJoinService classroomJoinService(ClassroomDomainRepository classroomDomainRepository, ClassroomSettingsDomainRepository classroomSettingsDomainRepository,
                                                     ClassroomStudentDomainRepository classroomStudentDomainRepository) {
        return new ClassroomJoinService(classroomDomainRepository,classroomSettingsDomainRepository, classroomStudentDomainRepository);
    }

    @Bean
    public UpdateClassroomService updateClassroomService(ClassroomDomainRepository classroomDomainRepository) {
        return new UpdateClassroomService(classroomDomainRepository);
    }
}
