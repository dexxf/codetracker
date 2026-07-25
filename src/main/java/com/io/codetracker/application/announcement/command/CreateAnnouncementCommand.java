package com.io.codetracker.application.announcement.command;

import com.io.codetracker.domain.announcement.valueobject.AttachmentType;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record CreateAnnouncementCommand(
        UUID classroomId,
        UUID authorId,
        String message,
        List<AttachmentUpload> attachments,
        Instant now
) {

    public record AttachmentUpload(
            byte[] content,
            AttachmentType type
    ) { }

}