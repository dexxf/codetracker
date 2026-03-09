package com.io.codetracker.application.activity.command;

import com.io.codetracker.domain.activity.valueObject.ActivityStatus;

import java.time.LocalDateTime;

public record EditActivityCommand(String userId,String classroomId,String activityId, String title, String description, LocalDateTime dueDate, ActivityStatus status,
                                  Integer maxScore) {
}
