package com.io.kira.application.announcement.port.out;

import java.util.UUID;

public interface ClassroomAnnouncementAppRepository {
    boolean existsByClassroomId(UUID classroomId);
    boolean isClassroomInstructor(UUID classroomId, UUID userId);
    boolean isActiveClassroomStudent(UUID classroomId, UUID userId);
}
