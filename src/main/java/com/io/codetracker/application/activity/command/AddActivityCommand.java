package com.io.codetracker.application.activity.command;

import com.io.codetracker.domain.activity.valueObject.ActivityStatus;

import java.time.Instant;

public record AddActivityCommand(String classroomId, String instructorUserId, String title, String description,
                                 Instant dueDate, Integer maxScore, ActivityStatus status) {
}
