package com.io.codetracker.adapter.classroom.in.dto.response;

public record GetClassroomStatsResponse(boolean success,String classroomId, long totalActivities, long activeActivities, long totalStudents, String message) {
    
    public static GetClassroomStatsResponse success(String classroomId, long totalStudents, long totalActivities, long activeActivities) {
        return new GetClassroomStatsResponse(true,classroomId, totalActivities, activeActivities, totalStudents, "Successfully retrieved Classroom Stats");
    }

    public static GetClassroomStatsResponse fail(String classroomId,String message) {
        return new GetClassroomStatsResponse(false,classroomId,0, 0, 0, message);
    }
}
