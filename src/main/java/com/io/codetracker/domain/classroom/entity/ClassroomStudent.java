package com.io.codetracker.domain.classroom.entity;

import com.io.codetracker.domain.classroom.valueObject.StudentStatus;

import java.time.LocalDateTime;

public final class ClassroomStudent {

    private final String classroomId;
    private final String studentUserId;
    private StudentStatus status;
    private LocalDateTime lastActiveAt;
    private final LocalDateTime joinedAt;
    private LocalDateTime leftAt;

    public ClassroomStudent(String classroomId, String studentUserId, StudentStatus status, LocalDateTime lastActiveAt, LocalDateTime joinedAt, LocalDateTime leftAt) {
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

    public LocalDateTime getLastActiveAt() {
        return lastActiveAt;
    }

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    public LocalDateTime getLeftAt() {
        return leftAt;
    }

    public void kick() {
        if (status == StudentStatus.DROPPED || status == StudentStatus.KICKED) {
            throw new IllegalStateException("Cannot kick a student who has already left or been kicked.");
        }
        this.status = StudentStatus.KICKED;
        this.leftAt = LocalDateTime.now();
    }

    public void drop() {
        if (status == StudentStatus.KICKED || status == StudentStatus.DROPPED) {
            throw new IllegalStateException("Student has already left or been kicked.");
        }
        this.status = StudentStatus.DROPPED;
        this.leftAt = LocalDateTime.now();
    }

    public void markActive() {
        if (status != StudentStatus.ACTIVE) {
            throw new IllegalStateException("Only active students can be marked active.");
        }
        this.lastActiveAt = LocalDateTime.now();
    }

    public void activate() {
        if (status != StudentStatus.PENDING) {
            throw new IllegalStateException("Only pending students can be activated.");
        }
        this.status = StudentStatus.ACTIVE;
        this.lastActiveAt = LocalDateTime.now();
    }
}