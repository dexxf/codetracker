package com.io.kira.application.announcement.result;

import com.io.kira.domain.announcement.entity.Announcement;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record AnnouncementViewData(
        UUID announcementId,
        UUID classroomId,
        UUID authorId,
        String message,
        List<AttachmentData> attachments,
        Instant createdAt,
        Instant updatedAt
) {
    public static AnnouncementViewData from(Announcement announcement) {
        List<AttachmentData> attachmentData = announcement.getAttachments()
                .stream()
                .map(attachment -> new AttachmentData(
                        attachment.attachmentId(),
                        attachment.url(),
                        attachment.type(),
                        attachment.resourceType()
                ))
                .toList();

        return new AnnouncementViewData(
                announcement.getAnnouncementId(),
                announcement.getClassroomId(),
                announcement.getAuthorId(),
                announcement.getMessage(),
                attachmentData,
                announcement.getCreatedAt(),
                announcement.getUpdatedAt()
        );
    }
}
