package com.io.codetracker.domain.classroom.entity;

import java.time.LocalDateTime;

import com.io.codetracker.domain.classroom.valueObject.ClassroomStatus;

public final class Classroom {
    
    private final String classroomId;
    private final String instructorUserId;
    private String name;
    private String description;
    private final String classCode;
    private ClassroomStatus status;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Classroom(String classroomId, String instructorUserId, String name, String description, String classCode, ClassroomStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.classroomId = classroomId;
        this.instructorUserId = instructorUserId;
        this.name = name;
        this.description = description;
        this.classCode = classCode;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getClassroomId() {
        return classroomId;
    }

    public String getInstructorUserId() {
        return instructorUserId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getClassCode() {
        return classCode;
    }

    public ClassroomStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    private void refreshUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }

    public void activate() {
        this.status = ClassroomStatus.ACTIVE;
        refreshUpdatedAt();
    }

    public void close() {
        this.status = ClassroomStatus.CLOSED;
        refreshUpdatedAt();
    }

    public void archive() {
        this.status = ClassroomStatus.ARCHIVED;
        refreshUpdatedAt();
    }

    public void updateName(String name) {
        this.name = name;
        refreshUpdatedAt();
    }

    public void updateDescription(String description) {
        this.description = description;
        refreshUpdatedAt();
    }
}