package com.io.kira.adapter.activity.out.persistence.repository;


import java.util.UUID;
import com.io.kira.infrastructure.user.persistence.repository.JpaUserRepository;
import com.io.kira.domain.activity.repository.ActivityUserDomainPort;
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

