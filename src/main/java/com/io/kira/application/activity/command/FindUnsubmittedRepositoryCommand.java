package com.io.kira.application.activity.command;


import java.util.UUID;
public record FindUnsubmittedRepositoryCommand(UUID userId, UUID classroomId) {
}

