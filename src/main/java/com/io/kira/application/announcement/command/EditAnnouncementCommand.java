package com.io.kira.application.announcement.command;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record EditAnnouncementCommand(
        UUID announcementId,
        UUID classroomId,
        UUID editorId,
        String message,
        List<AttachmentUpload> newAttachments,
        List<UUID> attachmentIdsToRemove,
        Instant now
) {

    public record AttachmentUpload(
            byte[] content,
            String filename
    ) { }

}