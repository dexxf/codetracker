package com.io.codetracker.domain.classroom.repository;

import com.io.codetracker.domain.classroom.entity.ClassroomSettings;

import java.util.Optional;
import java.util.UUID;

public interface ClassroomSettingsDomainRepository {
    Optional<ClassroomSettings> findByClassroomId(UUID classroomId);
}
