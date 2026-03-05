package com.io.codetracker.infrastructure.classroom.persistence.repository;

import com.io.codetracker.infrastructure.classroom.persistence.entity.ClassroomSettingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaClassroomSettingsRepository extends JpaRepository<ClassroomSettingsEntity, String> {
    Optional<ClassroomSettingsEntity> findByClassroomId(String classroomId);
}