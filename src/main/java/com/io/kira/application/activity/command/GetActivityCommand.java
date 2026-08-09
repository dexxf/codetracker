package com.io.kira.application.activity.command;


import java.util.UUID;
public record GetActivityCommand(UUID classroomId, UUID userId) {
}

