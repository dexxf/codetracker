package com.io.codetracker.application.classroom.command;

public record EditClassroomCommand(
    String userId,
    String classroomId,
    String name,
    String description,
    Integer maxStudents
) {
}
