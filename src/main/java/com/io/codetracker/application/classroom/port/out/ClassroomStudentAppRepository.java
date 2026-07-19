package com.io.codetracker.application.classroom.port.out;


import java.util.UUID;
import com.io.codetracker.domain.classroom.entity.ClassroomStudent;
import com.io.codetracker.domain.classroom.valueObject.StudentStatus;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ClassroomStudentAppRepository {
    boolean save(ClassroomStudent classroomStudent);
    boolean existsByClassroomIdAndStudentUserId(UUID classroomId, UUID studentUserId);
    List<ClassroomStudent> findActiveEnrollmentsWithActiveClassroom(UUID studentUserId);
    Map<UUID, Long> countActiveClassroomStudentByClassroomIds(List<UUID> classroomIds);
    List<ClassroomStudent> findClassroomStudents(UUID classroomId, StudentStatus status, boolean ascending);
    long countActiveClassroomStudentByClassroomId(UUID classroomId);
    Optional<ClassroomStudent> findByClassroomIdAndStudentUserId(UUID classroomId, UUID studentUserId);
}

