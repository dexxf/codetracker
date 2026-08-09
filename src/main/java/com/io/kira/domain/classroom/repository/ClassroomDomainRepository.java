package com.io.kira.domain.classroom.repository;

import com.io.kira.domain.classroom.entity.Classroom;

import java.util.Optional;
import java.util.UUID;

public interface ClassroomDomainRepository {
    boolean existsByClassroomId(UUID classroomId);
    boolean existsByActiveCode(String code);
    Optional<Classroom> findByClassroomId(UUID classroomId);
    Optional<Classroom> findByClassCode(String classCode);
}
