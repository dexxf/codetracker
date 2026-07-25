package com.io.codetracker.domain.announcement.entity;

import com.io.codetracker.domain.announcement.exception.AnnouncementMessageTooLongException;
import com.io.codetracker.domain.announcement.exception.EmptyAnnouncementMessageException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public final class Announcement {

    private static final int MAX_MESSAGE_LENGTH = 5000;

    private final UUID announcementId;
    private final UUID classroomId;
    private final UUID authorId;
    private String message;
    private final List<AnnouncementAttachment> attachments;
    private final Instant createdAt;
    private Instant updatedAt;

    private Announcement(
            UUID announcementId,
            UUID classroomId,
            UUID authorId,
            String message,
            List<AnnouncementAttachment> attachments,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.announcementId = Objects.requireNonNull(announcementId);
        this.classroomId = Objects.requireNonNull(classroomId);
        this.authorId = Objects.requireNonNull(authorId);
        this.createdAt = Objects.requireNonNull(createdAt);
        this.updatedAt = Objects.requireNonNull(updatedAt);
        this.attachments = new ArrayList<>(Objects.requireNonNull(attachments));

        validateMessage(message);
        this.message = message.trim();
    }

    public static Announcement create(
            UUID classroomId,
            UUID authorId,
            String message,
            List<AnnouncementAttachment> attachments,
            Instant now
    ) {
        Announcement announcement = new Announcement(
                UUID.randomUUID(),
                classroomId,
                authorId,
                message,
                new ArrayList<>(),
                now,
                now
        );
        attachments.forEach(announcement::addAttachment);
        return announcement;
    }

    public static Announcement reconstitute(
            UUID announcementId,
            UUID classroomId,
            UUID authorId,
            String message,
            List<AnnouncementAttachment> attachments,
            Instant createdAt,
            Instant updatedAt
    ) {
        return new Announcement(
                announcementId,
                classroomId,
                authorId,
                message,
                attachments,
                createdAt,
                updatedAt
        );
    }

    public void addAttachment(AnnouncementAttachment attachment) {
        Objects.requireNonNull(attachment);
        attachments.add(attachment);
    }

    public void removeAttachment(UUID attachmentId) {
        attachments.removeIf(attachment -> attachment.attachmentId().equals(attachmentId));
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

    public List<AnnouncementAttachment> attachments() {
        return Collections.unmodifiableList(attachments);
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }
}