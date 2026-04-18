package com.io.codetracker.domain.classroom.entity;

import com.io.codetracker.domain.classroom.valueObject.StudentStatus;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public final class ClassroomStudent {

    private final String classroomId;
    private final String studentUserId;
    private StudentStatus status;
    private Instant lastActiveAt;
    private final Instant joinedAt;
    private Instant leftAt;

    public ClassroomStudent(String classroomId, String studentUserId, StudentStatus status, Instant lastActiveAt, Instant joinedAt, Instant leftAt) {
        this.classroomId = classroomId;
        this.studentUserId = studentUserId;
        this.status = status;
        this.lastActiveAt = lastActiveAt;
        this.joinedAt = joinedAt;
        this.leftAt = leftAt;
    }

    public String getClassroomId() {
        return classroomId;
    }

    public String getStudentUserId() {
        return studentUserId;
    }

    public StudentStatus getStatus() {
        return status;
    }

    public Instant getLastActiveAt() {
        return lastActiveAt;
    }

    public Instant getJoinedAt() {
        return joinedAt;
    }

    public Instant getLeftAt() {
        return leftAt;
    }

    public void kick() {
        if (status == StudentStatus.DROPPED || status == StudentStatus.KICKED) {
            throw new IllegalStateException("Cannot kick a student who has already left or been kicked.");
        }
        this.status = StudentStatus.KICKED;
        this.leftAt = Instant.now();
    }

    public void drop() {
        if (status == StudentStatus.KICKED || status == StudentStatus.DROPPED) {
            throw new IllegalStateException("Student has already left or been kicked.");
        }
        this.status = StudentStatus.DROPPED;
        this.leftAt = Instant.now();
    }

    public void markActive() {
        if (status != StudentStatus.ACTIVE) {
            throw new IllegalStateException("Only active students can be marked active.");
        }
        this.lastActiveAt = Instant.now();
    }

    public void activate() {
        if (status != StudentStatus.PENDING) {
            throw new IllegalStateException("Only pending students can be activated.");
        }
        this.status = StudentStatus.ACTIVE;
        this.lastActiveAt = Instant.now();
    }
}