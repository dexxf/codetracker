package com.io.codetracker.application.announcement.result;

import com.io.codetracker.domain.announcement.entity.Announcement;
import com.io.codetracker.domain.announcement.entity.AnnouncementAttachment;

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
                announcement.announcementId(),
                announcement.classroomId(),
                announcement.authorId(),
                announcement.message(),
                announcement.attachments(),
                announcement.updatedAt()
        );
    }
}