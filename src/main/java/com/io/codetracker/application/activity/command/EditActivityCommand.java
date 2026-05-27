package com.io.codetracker.application.activity.command;

import com.io.codetracker.domain.activity.valueObject.ActivityStatus;

import java.time.Instant;

public record EditActivityCommand(String userId,String classroomId,String activityId, String title, String description, Instant dueDate, ActivityStatus status,
                                  Integer maxScore) {
}
