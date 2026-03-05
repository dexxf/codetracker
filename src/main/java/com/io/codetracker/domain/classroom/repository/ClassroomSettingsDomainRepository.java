package com.io.codetracker.domain.classroom.repository;

import com.io.codetracker.domain.classroom.entity.ClassroomSettings;

import java.util.Optional;

public interface ClassroomSettingsDomainRepository {
    Optional<ClassroomSettings> findByClassroomId(String classroomId);
}
