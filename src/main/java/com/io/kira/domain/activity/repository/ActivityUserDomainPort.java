package com.io.kira.domain.activity.repository;


import java.util.UUID;
public interface ActivityUserDomainPort {
    boolean existsByUserId(UUID userId);
}

