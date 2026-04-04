package com.io.codetracker.application.activity.port.out;

public interface ActivityClassroomAppPort {
    boolean existsByClassroomId(String s);
    boolean existsByClassroomIdAndInstructorUserId(String classroomId, String userId);
    String findClassroomOwnerByClassroomId(String classroomId);
    boolean existsByClassroomIdAndStudentUserId(String classroomId, String userId);
    boolean existsByClassroomIdAndActivityId(String classroomId, String activityId);
}
