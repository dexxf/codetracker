package com.io.codetracker.application.activity.port.out;

public interface ActivityClassroomStudentAppPort {
    boolean existsByClassroomIdAndStudentUserId(String classroomId, String studentUserId);
}
