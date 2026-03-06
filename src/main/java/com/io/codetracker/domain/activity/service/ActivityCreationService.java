package com.io.codetracker.domain.activity.service;

import com.io.codetracker.common.result.Result;
import com.io.codetracker.domain.activity.entity.Activity;
import com.io.codetracker.domain.activity.factory.ActivityFactory;
import com.io.codetracker.domain.activity.repository.ActivityDomainRepository;
import com.io.codetracker.domain.activity.repository.ActivityUserDomainPort;
import com.io.codetracker.domain.activity.result.ActivityCreationResult;
import com.io.codetracker.domain.activity.valueObject.ActivityStatus;

import java.time.LocalDateTime;

public final class ActivityCreationService {

    private static final int MAX_TITLE_LENGTH = 100;
    private static final int MIN_TITLE_LENGTH = 3;
    private static final int MAX_DESCRIPTION_LENGTH = 500;
    private static final int MIN_MAX_SCORE = 0;
    private static final int MAX_MAX_SCORE = 1000;

    private final ActivityDomainRepository activityDomainRepository;
    private final ActivityFactory factory;
    private final ActivityUserDomainPort activityUserDomainPort;

    public ActivityCreationService(ActivityDomainRepository activityDomainRepository, ActivityFactory factory, ActivityUserDomainPort activityUserDomainPort) {
        this.activityDomainRepository = activityDomainRepository;
        this.factory = factory;
        this.activityUserDomainPort = activityUserDomainPort;
    }

    public Result<Activity, ActivityCreationResult> create(String classroomId, String instructorUserId, String title,
                                                           String description, LocalDateTime dueDate, Integer maxScore, ActivityStatus status) {

        if (classroomId == null || classroomId.isBlank()) {
            return Result.fail(ActivityCreationResult.INVALID_CLASSROOM_ID);
        }

        if (instructorUserId == null || instructorUserId.isBlank()) {
            return Result.fail(ActivityCreationResult.INVALID_INSTRUCTOR_ID);
        }

        if (title == null || title.isBlank() || title.length() < MIN_TITLE_LENGTH || title.length() > MAX_TITLE_LENGTH) {
            return Result.fail(ActivityCreationResult.INVALID_TITLE);
        }

        if (description != null && description.length() > MAX_DESCRIPTION_LENGTH) {
            return Result.fail(ActivityCreationResult.INVALID_DESCRIPTION);
        }

        if (dueDate != null && dueDate.isBefore(LocalDateTime.now())) {
            return Result.fail(ActivityCreationResult.INVALID_DUE_DATE);
        }

        if (maxScore != null && (maxScore < MIN_MAX_SCORE || maxScore > MAX_MAX_SCORE)) {
            return Result.fail(ActivityCreationResult.INVALID_MAX_SCORE);
        }

        if (status == null) {
            return Result.fail(ActivityCreationResult.INVALID_STATUS);
        }

        if(!activityUserDomainPort.existsByUserId(instructorUserId))
            return Result.fail(ActivityCreationResult.INSTRUCTOR_NOT_FOUND);

        Activity activity = factory.create(classroomId, instructorUserId, title, description, dueDate, maxScore, status);

        if (activityDomainRepository.existsByClassroomIdAndActivityId(classroomId, activity.getActivityId())) {
            return Result.fail(ActivityCreationResult.ACTIVITY_ALREADY_EXISTS);
        }

        return Result.ok(activity);
    }
}