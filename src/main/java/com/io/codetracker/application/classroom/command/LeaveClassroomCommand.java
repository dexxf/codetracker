package com.io.codetracker.application.classroom.command;

public record LeaveClassroomCommand(
        String classroomId,
        String userId
) {
}
