package com.io.codetracker.domain.announcement.entity;

import com.io.codetracker.domain.announcement.exception.AnnouncementMessageTooLongException;
import com.io.codetracker.domain.announcement.exception.EmptyAnnouncementMessageException;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public final class Announcement {

    private static final int MAX_MESSAGE_LENGTH = 5000;

    private final UUID announcementId;
    private final UUID classroomId;
    private final UUID authorId;
    private String message;
    private final Instant createdAt;
    private Instant updatedAt;

    private Announcement(
            UUID announcementId,
            UUID classroomId,
            UUID authorId,
            String message,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.announcementId = Objects.requireNonNull(announcementId);
        this.classroomId = Objects.requireNonNull(classroomId);
        this.authorId = Objects.requireNonNull(authorId);
        this.createdAt = Objects.requireNonNull(createdAt);
        this.updatedAt = Objects.requireNonNull(updatedAt);

        validateMessage(message);
        this.message = message.trim();
    }

    public static Announcement create(
            UUID classroomId,
            UUID authorId,
            String message,
            Instant now
    ) {
        return new Announcement(
                UUID.randomUUID(),
                classroomId,
                authorId,
                message,
                now,
                now
        );
    }

    public static Announcement reconstitute(
            UUID announcementId,
            UUID classroomId,
            UUID authorId,
            String message,
            Instant createdAt,
            Instant updatedAt
    ) {
        return new Announcement(
                announcementId,
                classroomId,
                authorId,
                message,
                createdAt,
                updatedAt
        );
    }

    public void changeMessage(String message, Instant now) {
        validateMessage(message);

        this.message = message.trim();
        this.updatedAt = Objects.requireNonNull(now);
    }


    private static void validateMessage(String message) {
        if (message == null || message.isBlank()) {
            throw new EmptyAnnouncementMessageException();
        }

        if (message.length() > MAX_MESSAGE_LENGTH) {
            throw new AnnouncementMessageTooLongException(MAX_MESSAGE_LENGTH);
        }
    }
    public UUID announcementId() {
        return announcementId;
    }

    public UUID classroomId() {
        return classroomId;
    }

    public UUID authorId() {
        return authorId;
    }

    public String message() {
        return message;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }
}