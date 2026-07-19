package com.io.codetracker.application.classroom.command;


import java.util.UUID;
public record GetClassroomRecentActivitiesCommand(
        UUID userId,
        String classroomId,
        int limit
) {
}

