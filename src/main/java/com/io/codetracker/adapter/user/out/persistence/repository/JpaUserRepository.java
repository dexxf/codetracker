package com.io.codetracker.adapter.user.out.persistence.repository;

import com.io.codetracker.infrastructure.user.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<UserEntity, String> {
}
