package com.io.kira.application.activity.port.out;


import java.util.UUID;
public interface ActivityClassroomStudentAppPort {
    boolean existsByClassroomIdAndStudentUserId(UUID classroomId, UUID studentUserId);
}

