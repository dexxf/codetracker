package com.io.codetracker.infrastructure.activity.id;

import com.io.codetracker.common.id.IDGenerator;
import com.io.codetracker.domain.activity.repository.ActivityDomainRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public final class ActivityIDGenerator implements IDGenerator {

    private final ActivityDomainRepository repository;

    public ActivityIDGenerator(ActivityDomainRepository repository) {
        this.repository = repository;
    }

    @Override
    public String generate() {
        String id;

        while (true) {
            id = "act-" + UUID.randomUUID().toString().replace("-", "");
            if (!repository.existsById(id)) return id;
        }
    }
}