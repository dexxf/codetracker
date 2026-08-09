package com.io.codetracker.infrastructure.classroom.persistence.repository;

import com.io.codetracker.infrastructure.classroom.persistence.entity.ClassroomSettingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaClassroomSettingsRepository extends JpaRepository<ClassroomSettingsEntity, UUID> {
    Optional<ClassroomSettingsEntity> findByClassroomId(UUID classroomId);
}