package com.io.kira.domain.classroom.repository;

import com.io.kira.domain.classroom.entity.ClassroomSettings;

import java.util.Optional;
import java.util.UUID;

public interface ClassroomSettingsDomainRepository {
    Optional<ClassroomSettings> findByClassroomId(UUID classroomId);
}
