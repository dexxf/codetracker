package com.io.kira.application.announcement.result;

import com.io.kira.domain.announcement.entity.Announcement;
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
        List<AttachmentData> attachmentData = announcement.getAttachments()
                .stream()
                .map(a -> new AttachmentData(a.getAttachmentId(), a.getUrl(), a.getType(), a.getResourceType()))
                .toList();

        return new CreateAnnouncementResult(
                announcement.getAnnouncementId(),
                announcement.getMessage(),
                attachmentData,
                announcement.getCreatedAt()
        );
    }
}
