package com.io.codetracker.domain.classroom.repository;

public interface ClassroomDomainRepository {
    boolean existsByClassroomId(String classroomId);
    boolean existsByActiveCode(String code);
}