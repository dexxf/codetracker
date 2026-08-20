package com.io.kira.application.activity.port.out;


import java.util.UUID;
import java.util.Optional;

public interface ActivityClassroomAppPort {
    boolean existsByClassroomId(UUID s);
    boolean existsByClassroomIdAndInstructorUserId(UUID classroomId, UUID userId);
    boolean existsByClassroomIdAndStudentUserId(UUID classroomId, UUID userId);
    boolean existsByClassroomIdAndActivityId(UUID classroomId, UUID activityId);
    Optional<Integer> findMaxScoreByClassroomIdAndActivityId(UUID classroomId, UUID activityId);
}

