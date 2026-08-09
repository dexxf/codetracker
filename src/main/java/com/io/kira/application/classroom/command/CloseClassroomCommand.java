package com.io.kira.application.classroom.command;


import java.util.UUID;
public record CloseClassroomCommand(
    UUID userId,
    UUID classroomId
) {
}

