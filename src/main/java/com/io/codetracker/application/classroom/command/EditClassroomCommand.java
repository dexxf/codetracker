package com.io.codetracker.application.classroom.command;


import java.util.UUID;
public record EditClassroomCommand(
    UUID userId,
    String classroomId,
    String name,
    String description,
    Integer maxStudents
) {
}

