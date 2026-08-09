package com.io.kira.application.classroom.command;


import java.util.UUID;
public record GetClassroomRecentActivitiesCommand(
        UUID userId,
        UUID classroomId,
        int limit
) {
}

