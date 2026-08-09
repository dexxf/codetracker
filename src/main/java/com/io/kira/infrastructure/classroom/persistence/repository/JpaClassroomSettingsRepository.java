package com.io.kira.infrastructure.classroom.persistence.repository;

import com.io.kira.infrastructure.classroom.persistence.entity.ClassroomSettingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaClassroomSettingsRepository extends JpaRepository<ClassroomSettingsEntity, UUID> {
    Optional<ClassroomSettingsEntity> findByClassroomId(UUID classroomId);
}