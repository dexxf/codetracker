package com.io.codetracker.infrastructure.auth.config;

import com.io.codetracker.domain.auth.service.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AuthBeanConfig {

    @Bean
    public PasswordService passwordService(@Qualifier("passwordEncoder") PasswordHasher hasher) {
        return new PasswordService(hasher);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public RestClient restClient(@Value("${rest-client.connect-timeout-ms}") int connectTimeout, @Value("${rest-client.read-timeout-ms}") int readTimeout) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(connectTimeout);
        factory.setReadTimeout(readTimeout);

        return RestClient.builder()
                .requestFactory(factory)
                .build();
    }

}
