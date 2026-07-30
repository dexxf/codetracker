package com.io.codetracker.application.announcement.result;

import com.io.codetracker.domain.announcement.entity.Announcement;

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
        List<AttachmentData> attachmentData = announcement.attachments()
                .stream()
                .map(attachment -> new AttachmentData(
                        attachment.attachmentId(),
                        attachment.url(),
                        attachment.type(),
                        attachment.resourceType()
                ))
                .toList();

        return new AnnouncementViewData(
                announcement.announcementId(),
                announcement.classroomId(),
                announcement.authorId(),
                announcement.message(),
                attachmentData,
                announcement.createdAt(),
                announcement.updatedAt()
        );
    }
}
