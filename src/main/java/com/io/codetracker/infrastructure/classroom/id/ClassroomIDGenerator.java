package com.io.codetracker.infrastructure.classroom.id;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.io.codetracker.common.id.IDGenerator;
import com.io.codetracker.domain.classroom.repository.ClassroomDomainRepository;

@Component
public final class ClassroomIDGenerator implements IDGenerator {

    private final ClassroomDomainRepository repository;

    public ClassroomIDGenerator(ClassroomDomainRepository repository) {
        this.repository = repository;
    }

    @Override
    public String generate() {
        String id;

        while (true) {
            id = "cl-" + UUID.randomUUID().toString().replace("-", "");
            if (!repository.existsByClassroomId(id)) return id;
        }

    }
}
