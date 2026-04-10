package com.io.codetracker.application.classroom.command;

public record GetClassroomRecentActivitiesCommand(
        String userId,
        String classroomId,
        int limit
) {
}
