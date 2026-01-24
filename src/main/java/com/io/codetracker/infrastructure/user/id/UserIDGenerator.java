package com.io.codetracker.infrastructure.user.id;

import com.io.codetracker.adapter.user.out.persistence.repository.JpaUserRepository;
import com.io.codetracker.common.id.IDGenerator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@AllArgsConstructor
public class UserIDGenerator implements IDGenerator {

    private final JpaUserRepository repository;

    @Override
    public String generate() {
        String id;

        while (true) {
            id = "usr-" + UUID.randomUUID();
            if (!repository.existsById(id)) return id;
        }
    }
}
