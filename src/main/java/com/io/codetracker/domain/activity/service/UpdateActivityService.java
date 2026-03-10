package com.io.codetracker.domain.activity.service;

import com.io.codetracker.common.result.Result;
import com.io.codetracker.domain.activity.entity.Activity;
import com.io.codetracker.domain.activity.repository.ActivityDomainRepository;
import com.io.codetracker.domain.activity.result.EditActivityResult;
import com.io.codetracker.domain.activity.valueObject.ActivityStatus;

import java.time.LocalDateTime;

public final class UpdateActivityService {

    private static final int MAX_TITLE_LENGTH = 100;
    private static final int MIN_TITLE_LENGTH = 3;
    private static final int MAX_DESCRIPTION_LENGTH = 500;
    private static final int MIN_MAX_SCORE = 0;
    private static final int MAX_MAX_SCORE = 1000;

    private final ActivityDomainRepository activityDomainRepository;

    public UpdateActivityService(ActivityDomainRepository activityDomainRepository) {
        this.activityDomainRepository = activityDomainRepository;
    }

    public Result<Activity, EditActivityResult> updateAndValidate(String activityId, String title, String description, LocalDateTime dueDate,
                                                                  ActivityStatus newStatus, Integer maxScore) {

        Activity activity = activityDomainRepository.findByActivityId(activityId)
                .orElse(null);

        if (activity == null) {
            return Result.fail(EditActivityResult.ACTIVITY_NOT_FOUND);
        }

        if (title == null || title.isBlank() || title.length() < MIN_TITLE_LENGTH || title.length() > MAX_TITLE_LENGTH) {
            return Result.fail(EditActivityResult.TITLE_EMPTY);
        }

        if (description != null && description.length() > MAX_DESCRIPTION_LENGTH) {
            return Result.fail(EditActivityResult.INVALID_DESCRIPTION);
        }

        if (maxScore != null && (maxScore < MIN_MAX_SCORE || maxScore > MAX_MAX_SCORE)) {
            return Result.fail(EditActivityResult.MAX_SCORE_INVALID);
        }

        if (dueDate != null && dueDate.isBefore(LocalDateTime.now())) {
            return Result.fail(EditActivityResult.DUE_DATE_INVALID);
        }

        ActivityStatus currentStatus = activity.getStatus();

        switch (currentStatus) {
            case DRAFT:
                if (newStatus != ActivityStatus.DRAFT && newStatus != ActivityStatus.PUBLISHED) {
                    return Result.fail(EditActivityResult.INVALID_STATUS_TRANSITION);
                }
                break;
            case PUBLISHED:
                if (newStatus != ActivityStatus.PUBLISHED && newStatus != ActivityStatus.CLOSED) {
                    return Result.fail(EditActivityResult.INVALID_STATUS_TRANSITION);
                }
                break;
            case CLOSED:
                if (newStatus != ActivityStatus.CLOSED && newStatus != ActivityStatus.ARCHIVED) {
                    return Result.fail(EditActivityResult.INVALID_STATUS_TRANSITION);
                }
                break;
            case ARCHIVED:
                return Result.fail(EditActivityResult.ACTIVITY_ARCHIVED);
        }

        activity.setTitle(title);
        activity.setDescription(description);
        activity.setDueDate(dueDate);
        activity.setMaxScore(maxScore);

        if (newStatus != currentStatus) {
            switch (newStatus) {
                case PUBLISHED -> activity.publishActivity();
                case CLOSED -> activity.closeActivity();
                case ARCHIVED -> activity.archiveActivity();
                default -> activity.setStatus(newStatus);
            }
        } else {
            activity.setUpdatedAt(LocalDateTime.now());
        }

        return Result.ok(activity);
    }
}