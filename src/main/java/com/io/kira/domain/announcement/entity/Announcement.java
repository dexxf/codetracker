package com.io.kira.domain.announcement.entity;

import com.io.kira.domain.announcement.exception.AnnouncementMessageTooLongException;

import java.time.Instant;
import java.util.*;

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
        this.message = Objects.isNull(message) ? null : message.trim();
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
        Objects.requireNonNull(attachments);
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
        Objects.requireNonNull(attachment, "attachments must not be null");
        attachments.add(attachment);
    }

    public void removeAttachment(UUID attachmentId) {
        attachments.removeIf(attachment -> attachment.getAttachmentId().equals(attachmentId));
    }

    private static void validateMessage(String message) {
        if (message != null && message.trim().length() > MAX_MESSAGE_LENGTH) {
            throw new AnnouncementMessageTooLongException(MAX_MESSAGE_LENGTH);
        }
    }

    public UUID getAnnouncementId() {
        return announcementId;
    }

    public UUID getClassroomId() {
        return classroomId;
    }

    public UUID getAuthorId() {
        return authorId;
    }

    public String getMessage() {
        return message;
    }

    public List<AnnouncementAttachment> getAttachments() {
        return Collections.unmodifiableList(attachments);
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Optional<AnnouncementAttachment> findAttachment(UUID attachmentId) {
        return attachments.stream().filter(a -> a.getAttachmentId().equals(attachmentId)).findFirst();
    }

    public void updateMessage(String message, Instant now) {
        validateMessage(message);
        this.message = Objects.isNull(message) ? null : message.trim();
        this.updatedAt = now;
    }

    public void markUpdated(Instant now) {
        this.updatedAt = Objects.requireNonNull(now);
    }
}