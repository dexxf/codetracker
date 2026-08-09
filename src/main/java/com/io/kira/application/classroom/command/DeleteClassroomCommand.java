package com.io.kira.application.classroom.command;


import java.util.UUID;
public record DeleteClassroomCommand(
    UUID userId,
    UUID classroomId
) {
}
