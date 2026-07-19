package com.io.codetracker.application.activity.port.out;


import java.util.UUID;
import java.util.Optional;

public interface ActivityClassroomAppPort {
    boolean existsByClassroomId(String s);
    boolean existsByClassroomIdAndInstructorUserId(String classroomId, UUID userId);
    UUID findClassroomOwnerByClassroomId(String classroomId);
    boolean existsByClassroomIdAndStudentUserId(String classroomId, UUID userId);
    boolean existsByClassroomIdAndActivityId(String classroomId, String activityId);
    Optional<Integer> findMaxScoreByClassroomIdAndActivityId(String classroomId, String activityId);
}

