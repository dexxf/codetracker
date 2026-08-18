package com.io.kira.application.announcement.result;

import com.io.kira.domain.announcement.entity.Announcement;
import com.io.kira.domain.announcement.entity.AnnouncementAttachment;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record EditAnnouncementResult(
        UUID announcementId,
        UUID classroomId,
        UUID authorId,
        String message,
        List<AnnouncementAttachment> attachments,
        Instant updatedAt
) {
    public static EditAnnouncementResult toResult(Announcement announcement) {
        return new EditAnnouncementResult(
                announcement.getAnnouncementId(),
                announcement.getClassroomId(),
                announcement.getAuthorId(),
                announcement.getMessage(),
                announcement.getAttachments(),
                announcement.getUpdatedAt()
        );
    }
}