package com.io.codetracker.application.activity.command;


import java.util.UUID;
public record GetActivityCommand(UUID classroomId, UUID userId) {
}

