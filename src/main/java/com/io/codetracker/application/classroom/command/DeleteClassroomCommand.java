package com.io.codetracker.application.classroom.command;

public record DeleteClassroomCommand(
    String userId,
    String classroomId
) {
}