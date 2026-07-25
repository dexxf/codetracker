package com.io.codetracker.domain.announcement.entity;

import com.io.codetracker.domain.announcement.exception.EmptyAttachmentPublicIdException;
import com.io.codetracker.domain.announcement.exception.EmptyAttachmentUrlException;
import com.io.codetracker.domain.announcement.exception.InvalidDisplayImageTypeException;
import com.io.codetracker.domain.announcement.valueobject.AttachmentType;

import java.util.Objects;
import java.util.UUID;

public final class AnnouncementAttachment {

    private final UUID attachmentId;
    private final String url;
    private final String publicId;
    private final AttachmentType type;
    private boolean displayImage;

    private AnnouncementAttachment(
            UUID attachmentId,
            String url,
            String publicId,
            AttachmentType type,
            boolean displayImage
    ) {
        this.attachmentId = Objects.requireNonNull(attachmentId);
        this.type = Objects.requireNonNull(type);
        this.displayImage = displayImage;

        validateUrl(url);
        this.url = url;

        validatePublicId(publicId);
        this.publicId = publicId;
    }

    public static AnnouncementAttachment create(
            String url,
            String publicId,
            AttachmentType type
    ) {
        return new AnnouncementAttachment(
                UUID.randomUUID(),
                url,
                publicId,
                type,
                false
        );
    }

    public static AnnouncementAttachment reconstitute(
            UUID attachmentId,
            String url,
            String publicId,
            AttachmentType type,
            boolean displayImage
    ) {
        return new AnnouncementAttachment(
                attachmentId,
                url,
                publicId,
                type,
                displayImage
        );
    }

    public void markAsDisplayImage() {
        if (type != AttachmentType.IMAGE) {
            throw new InvalidDisplayImageTypeException(type);
        }
        this.displayImage = true;
    }

    public void unmarkAsDisplayImage() {
        this.displayImage = false;
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

    public String publicId() {
        return publicId;
    }

    public AttachmentType type() {
        return type;
    }

    public boolean isDisplayImage() {
        return displayImage;
    }
}