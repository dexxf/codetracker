package com.io.codetracker.domain.activity.entity;

import com.io.codetracker.domain.activity.valueObject.ActivityStatus;

import java.time.LocalDateTime;

public class Activity {

    private final String activityId;
    private final String classroomId;
    private final String instructorUserId;
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private ActivityStatus status;
    private Integer maxScore;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Activity(String activityId ,String classroomId, String instructorUserId, String title, String description, LocalDateTime dueDate, ActivityStatus status, Integer maxScore, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.activityId = activityId;
        this.classroomId = classroomId;
        this.instructorUserId = instructorUserId;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
        this.maxScore = maxScore;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getActivityId() {
        return activityId;
    }

    public String getClassroomId() {
        return classroomId;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Integer getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Integer maxScore) {
        this.maxScore = maxScore;
    }

    public ActivityStatus getStatus() {
        return status;
    }

    public void setStatus(ActivityStatus status) {
        this.status = status;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInstructorUserId() {
        return instructorUserId;
    }

    public void publishActivity() {
        this.status = ActivityStatus.PUBLISHED;
        this.updatedAt = LocalDateTime.now();
    }

    public void closeActivity() {
        this.status = ActivityStatus.CLOSED;
        this.updatedAt = LocalDateTime.now();
    }

    public void archiveActivity() {
        this.status = ActivityStatus.ARCHIVED;
        this.updatedAt = LocalDateTime.now();
    }
}