package com.io.codetracker.domain.classroom.entity;


import java.util.UUID;
import java.time.Instant;

import com.io.codetracker.domain.classroom.valueObject.ClassroomStatus;

public final class Classroom {
    
    private final String classroomId;
    private final UUID instructorUserId;
    private String name;
    private String description;
    private final String classCode;
    private ClassroomStatus status;
    private final Instant createdAt;
    private Instant updatedAt;

    public Classroom(String classroomId, UUID instructorUserId, String name, String description, String classCode, ClassroomStatus status, Instant createdAt, Instant updatedAt) {
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

    public UUID getInstructorUserId() {
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

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    private void refreshUpdatedAt() {
        this.updatedAt = Instant.now();
    }

    public void activate() {
        this.status = ClassroomStatus.ACTIVE;
        refreshUpdatedAt();
    }

    public void close() {
        this.status = ClassroomStatus.CLOSED;
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

    public boolean isClosed() {
        return this.status == ClassroomStatus.CLOSED;
    }
}

