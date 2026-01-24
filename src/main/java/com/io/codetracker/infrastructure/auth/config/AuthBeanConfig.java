package com.io.codetracker.infrastructure.auth.config;

import com.io.codetracker.domain.auth.factory.AuthFactory;
import com.io.codetracker.domain.auth.repository.AuthDomainRepository;
import com.io.codetracker.domain.auth.service.AuthCreationService;
import com.io.codetracker.domain.auth.service.PasswordHasher;
import com.io.codetracker.domain.auth.service.PasswordService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthBeanConfig {

    @Bean
    public PasswordService passwordService(@Qualifier("passwordEncoder") PasswordHasher hasher) {
        return new PasswordService(hasher);
    }

    @Bean
    public AuthCreationService authCreationService(@Qualifier("defaultAuthFactory") AuthFactory factory,@Qualifier("domainAuthRepositoryImpl") AuthDomainRepository repository, PasswordService service) {
        return new AuthCreationService(factory,repository,service);
    }
}
