package com.io.codetracker.application.classroom.command;

public record CloseClassroomCommand(
    String userId,
    String classroomId
) {
}
