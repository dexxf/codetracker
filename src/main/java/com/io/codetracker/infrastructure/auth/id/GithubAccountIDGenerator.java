package com.io.codetracker.infrastructure.auth.id;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.io.codetracker.common.id.IDGenerator;
import com.io.codetracker.domain.auth.repository.GithubAccountDomainRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class GithubAccountIDGenerator implements IDGenerator {

    private final GithubAccountDomainRepository repository;

    @Override
    public String generate() {
            String id;

        while (true) {
            id = "g@" + UUID.randomUUID();
            if (!repository.existsById(id)) return id;
        }
    }
    
}
