package com.io.codetracker.application.classroom.command;


import java.util.UUID;
public record LeaveClassroomCommand(
        String classroomId,
        UUID userId
) {
}

