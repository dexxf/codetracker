package com.io.codetracker.application.activity.command;

public record FindUnsubmittedRepositoryCommand(String userId, String classroomId) {
}
