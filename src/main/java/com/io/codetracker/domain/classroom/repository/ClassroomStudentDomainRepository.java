package com.io.codetracker.domain.classroom.repository;


import java.util.UUID;
public interface ClassroomStudentDomainRepository {
    boolean existsByClassroomIdAndStudentUserId(UUID classroomId, UUID studentUserId);
    int countByClassroomId(UUID classroomId);
}

