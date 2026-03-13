package com.io.codetracker.application.classroom.port.out;

public interface ClassroomActivityAppPort {
    long countByClassroomId(String classroomId);
    long countActiveActivitiesByClassroomId(String classroomId);
}
