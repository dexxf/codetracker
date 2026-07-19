package com.io.codetracker.application.activity.command;


import java.util.UUID;
public record FindUnsubmittedRepositoryCommand(UUID userId, UUID classroomId) {
}

