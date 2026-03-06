package com.io.codetracker.domain.activity.repository;

public interface ActivityUserDomainPort {
    boolean existsByUserId(String userId);
}
