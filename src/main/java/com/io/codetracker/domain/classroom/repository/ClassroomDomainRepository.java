package com.io.codetracker.domain.classroom.repository;

import com.io.codetracker.domain.classroom.entity.Classroom;

import java.util.Optional;
import java.util.UUID;

public interface ClassroomDomainRepository {
    boolean existsByClassroomId(UUID classroomId);
    boolean existsByActiveCode(String code);
    Optional<Classroom> findByClassroomId(UUID classroomId);
    Optional<Classroom> findByClassCode(String classCode);
}
