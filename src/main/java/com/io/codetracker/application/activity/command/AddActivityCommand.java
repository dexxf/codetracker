package com.io.codetracker.application.activity.command;

import com.io.codetracker.domain.activity.valueObject.ActivityStatus;

import java.time.LocalDateTime;

public record AddActivityCommand(String classroomId, String instructorUserId, String title, String description,
                                 LocalDateTime dueDate, Integer maxScore, ActivityStatus status) {
}