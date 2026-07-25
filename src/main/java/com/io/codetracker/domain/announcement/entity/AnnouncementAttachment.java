package com.io.codetracker.domain.announcement.entity;

import com.io.codetracker.domain.announcement.exception.EmptyAttachmentPublicIdException;
import com.io.codetracker.domain.announcement.exception.EmptyAttachmentUrlException;
import com.io.codetracker.domain.announcement.valueobject.AttachmentType;

import java.util.Objects;
import java.util.UUID;

public final class AnnouncementAttachment {

    private final UUID attachmentId;
    private final String url;
    private final AttachmentType type;

    public AnnouncementAttachment(
            UUID attachmentId,
            String url,
            String publicId,
            AttachmentType type
    ) {
        this.attachmentId = Objects.requireNonNull(attachmentId);
        this.type = Objects.requireNonNull(type);

        validateUrl(url);
        this.url = url;

        validatePublicId(publicId);
    }

    private static void validateUrl(String url) {
        if (url == null || url.isBlank()) {
            throw new EmptyAttachmentUrlException();
        }
    }

    private static void validatePublicId(String publicId) {
        if (publicId == null || publicId.isBlank()) {
            throw new EmptyAttachmentPublicIdException();
        }
    }

    public UUID attachmentId() {
        return attachmentId;
    }

    public String url() {
        return url;
    }

    public AttachmentType type() {
        return type;
    }

}