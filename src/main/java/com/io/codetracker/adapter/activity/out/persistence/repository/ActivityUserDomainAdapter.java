package com.io.codetracker.adapter.activity.out.persistence.repository;


import java.util.UUID;
import com.io.codetracker.infrastructure.user.persistence.repository.JpaUserRepository;
import com.io.codetracker.domain.activity.repository.ActivityUserDomainPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class ActivityUserDomainAdapter implements ActivityUserDomainPort {

    private final JpaUserRepository jpa;

    @Override
    public boolean existsByUserId(UUID userId) {
        return jpa.existsById(userId);
    }
}

