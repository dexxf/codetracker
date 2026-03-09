package com.io.codetracker.application.activity.port.out;

public interface ActivityClassroomAppPort {
    boolean existsByClassroomId(String s);
    boolean existsByClassroomIdAndInstructorUserId(String classroomId, String userId);
}
