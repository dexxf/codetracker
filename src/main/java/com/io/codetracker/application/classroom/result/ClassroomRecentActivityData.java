package com.io.codetracker.application.classroom.result;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public record ClassroomRecentActivityData(
        String eventType,
        ZonedDateTime occurredAt,
        String studentUserId,
        String firstName,
        String lastName,
        String profileUrl,
        String activityId,
        String activityTitle,
        String repositoryName,
        String repositoryUrl
) {

    public static ClassroomRecentActivityData fromStudentJoined(ClassroomStudentJoinedData data) {
        return new ClassroomRecentActivityData(
                "STUDENT_JOINED",
                data.joinedAt().atZone(ZoneOffset.UTC),
                data.studentUserId(),
                data.firstName(),
                data.lastName(),
                data.profileUrl(),
                null,
                null,
                null,
                null
        );
    }

    public static ClassroomRecentActivityData fromRepositorySubmitted(ClassroomRepositorySubmissionData data) {
        return new ClassroomRecentActivityData(
                "REPOSITORY_SUBMITTED",
                data.submittedAt().atZone(ZoneOffset.UTC),
                data.studentUserId(),
                data.firstName(),
                data.lastName(),
                data.profileUrl(),
                data.activityId(),
                data.activityTitle(),
                data.repositoryName(),
                data.repositoryUrl()
        );
    }

    public static ClassroomRecentActivityData fromActivityCreated(ClassroomActivityCreatedData data) {
        return new ClassroomRecentActivityData(
                "ACTIVITY_CREATED",
                data.createdAt().atZone(ZoneOffset.UTC),
                null,
                null,
                null,
                null,
                data.activityId(),
                data.title(),
                null,
                null
        );
    }
}
