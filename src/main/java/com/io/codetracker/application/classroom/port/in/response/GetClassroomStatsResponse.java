package com.io.codetracker.application.classroom.port.in.response;

public record GetClassroomStatsResponse(boolean success,String classroomId, long totalActivities, long activeActivities, long totalStudents) {
    
    public static GetClassroomStatsResponse success(String classroomId, long totalStudents, long totalActivities, long activeActivities) {
        return new GetClassroomStatsResponse(true,classroomId, totalActivities, activeActivities, totalStudents);
    }

    public static GetClassroomStatsResponse fail(String classroomId,String message) {
        return new GetClassroomStatsResponse(false,classroomId,0, 0, 0);
    }
}
