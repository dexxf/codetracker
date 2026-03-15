package com.io.codetracker.adapter.classroom.in.dto.response;

import com.io.codetracker.application.classroom.result.ClassroomStats;

public record GetClassroomStatsResponse(long totalActivities, long activeActivities, long totalStudents, String message) {
    
    public static GetClassroomStatsResponse success(ClassroomStats stats) {
        return new GetClassroomStatsResponse(stats.totalActivities(), stats.activeActivities(), stats.totalStudents(), "Successfully retrieved Classroom Stats");
    }

    public static GetClassroomStatsResponse fail(String message) {
        return new GetClassroomStatsResponse(0, 0, 0, message);
    }
}
