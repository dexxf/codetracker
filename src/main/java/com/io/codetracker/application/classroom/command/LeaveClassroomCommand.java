package com.io.codetracker.application.classroom.command;


import java.util.UUID;
public record LeaveClassroomCommand(
        UUID classroomId,
        UUID userId
) {
}

