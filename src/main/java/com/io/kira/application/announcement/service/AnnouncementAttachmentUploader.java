package com.io.kira.application.announcement.service;

import com.io.kira.application.announcement.port.out.AnnouncementAttachmentStoragePort;
import com.io.kira.application.announcement.port.out.AttachmentTypeResolverPort;
import com.io.kira.domain.announcement.entity.AnnouncementAttachment;
import com.io.kira.domain.announcement.valueobject.AttachmentType;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.UUID;

public final class AnnouncementAttachmentUploader {

    private AnnouncementAttachmentUploader() {
    }

    public static AnnouncementAttachment upload(byte[] content, String filename, UUID classroomId, AnnouncementAttachmentStoragePort attachmentStorage, AttachmentTypeResolverPort typeResolver) throws IOException {
        UUID attachmentId = UUID.randomUUID();

        AttachmentType type = typeResolver.resolve(new ByteArrayInputStream(content), filename);
        var uploadedAttachment = attachmentStorage.upload(content, classroomId, attachmentId);

        return new AnnouncementAttachment(
                attachmentId,
                uploadedAttachment.url(),
                type,
                uploadedAttachment.resourceType()
        );
    }
}
