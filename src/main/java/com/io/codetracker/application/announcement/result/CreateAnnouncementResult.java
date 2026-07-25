package com.io.codetracker.application.announcement.result;

import com.io.codetracker.domain.announcement.entity.Announcement;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record CreateAnnouncementResult(
        UUID announcementId,
        String message,
        List<AttachmentData> attachments,
        Instant createdAt
) {

    public static CreateAnnouncementResult toResult(Announcement announcement) {
        List<AttachmentData> attachmentData = announcement.attachments()
                .stream()
                .map(a -> new AttachmentData(a.attachmentId(), a.url(), a.type()))
                .toList();

        return new CreateAnnouncementResult(
                announcement.announcementId(),
                announcement.message(),
                attachmentData,
                announcement.createdAt()
        );
    }
}