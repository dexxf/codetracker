package com.io.codetracker.domain.classroom.repository;

public interface ClassroomStudentDomainRepository {
    boolean existsByClassroomIdAndStudentUserId(String classroomId, String studentUserId);
}
