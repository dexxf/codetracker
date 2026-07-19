package com.io.codetracker.application.activity.port.out;


import java.util.UUID;
public interface ActivityClassroomStudentAppPort {
    boolean existsByClassroomIdAndStudentUserId(String classroomId, UUID studentUserId);
}

