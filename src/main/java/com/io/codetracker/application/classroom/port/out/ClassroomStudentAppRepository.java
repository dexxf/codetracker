package com.io.codetracker.application.classroom.port.out;

import com.io.codetracker.domain.classroom.entity.ClassroomStudent;
import com.io.codetracker.domain.classroom.valueObject.StudentStatus;

import java.util.List;
import java.util.Map;

public interface ClassroomStudentAppRepository {
    boolean save(ClassroomStudent classroomStudent);
    boolean existsByClassroomIdAndStudentUserId(String classroomId, String studentUserId);
    List<ClassroomStudent> findActiveEnrollmentsWithActiveClassroom(String studentUserId);
    Map<String, Integer> countByClassroomIds(List<String> classroomIds);
    List<ClassroomStudent> findClassroomStudents(String classroomId, StudentStatus status, boolean ascending);
    long countByClassroomId(String classroomId);
}