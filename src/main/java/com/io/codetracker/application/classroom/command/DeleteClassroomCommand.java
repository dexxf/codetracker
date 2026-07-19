package com.io.codetracker.application.classroom.command;


import java.util.UUID;
public record DeleteClassroomCommand(
    UUID userId,
    String classroomId
) {
}
