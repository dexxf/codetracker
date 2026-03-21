package com.io.codetracker.domain.classroom.repository;

import com.io.codetracker.domain.classroom.entity.Classroom;

import java.util.Optional;

public interface ClassroomDomainRepository {
    boolean existsByClassroomId(String classroomId);
    boolean existsByActiveCode(String code);
    Optional<Classroom> findByClassroomId(String classroomId);
    Optional<Classroom> findByClassCode(String classCode);
}