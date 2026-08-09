package com.io.kira.application.classroom.port.out;

import java.util.UUID;

public interface ClassroomActivityAppPort {
    long countByClassroomId(UUID classroomId);
    long countActiveActivitiesByClassroomId(UUID classroomId);
}
