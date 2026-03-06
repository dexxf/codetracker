package com.io.codetracker.domain.activity.factory;

import com.io.codetracker.domain.activity.entity.Activity;
import com.io.codetracker.domain.activity.valueObject.ActivityStatus;

import java.time.LocalDateTime;

public interface ActivityFactory {
    Activity create(String classroomId, String instructorUserId, String title, String description, LocalDateTime dueDate, Integer maxScore, ActivityStatus status);
}