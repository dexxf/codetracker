package com.io.kira.application.activity.command;


import java.util.UUID;
import com.io.kira.domain.activity.valueObject.ActivityStatus;

import java.time.Instant;

public record AddActivityCommand(UUID classroomId, UUID instructorUserId, String title, String description,
                                 Instant dueDate, Integer maxScore, ActivityStatus status) {
}

