package com.io.codetracker.domain.activity.repository;


import java.util.UUID;
public interface ActivityUserDomainPort {
    boolean existsByUserId(UUID userId);
}

