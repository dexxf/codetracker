package com.io.codetracker.application.classroom.port.out;

import com.io.codetracker.domain.classroom.entity.ClassroomStudent;

import java.util.List;
import java.util.Map;

public interface ClassroomStudentAppRepository {
    boolean save(ClassroomStudent classroomStudent);
    List<ClassroomStudent> findActiveEnrollmentsWithActiveClassroom(String studentUserId);
    Map<String, Integer> countByClassroomIds(List<String> classroomIds);
}