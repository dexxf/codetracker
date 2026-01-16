package com.io.codetracker.infrastructure.auth.id;

import com.io.codetracker.common.id.IDGenerator;
import com.io.codetracker.domain.auth.repository.AuthDomainRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@AllArgsConstructor
public class AuthIDGenerator implements IDGenerator {

    private final AuthDomainRepository repository;

    @Override
    public String generate() {
        String id;

        while (true) {
            id = "@t-" + UUID.randomUUID();
            if (!repository.existsById(id)) return id;
        }
    }
}
