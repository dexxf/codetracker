package com.io.kira.domain.announcement.entity;

import com.io.kira.domain.announcement.exception.EmptyAttachmentUrlException;
import com.io.kira.domain.announcement.valueobject.AttachmentType;

import java.util.Objects;
import java.util.UUID;

public final class AnnouncementAttachment {

    private final UUID attachmentId;
    private final String url;
    private final AttachmentType type;
    private final String resourceType;

    public AnnouncementAttachment(
            UUID attachmentId,
            String url,
            AttachmentType type,
            String resourceType
    ) {
        this.attachmentId = Objects.requireNonNull(attachmentId);
        this.type = Objects.requireNonNull(type);
        this.resourceType = Objects.requireNonNull(resourceType);

        validateUrl(url);
        this.url = url;

    }

    private static void validateUrl(String url) {
        if (url == null || url.isBlank()) {
            throw new EmptyAttachmentUrlException();
        }
    }

    public UUID getAttachmentId() {
        return attachmentId;
    }

    public String getUrl() {
        return url;
    }

    public AttachmentType getType() {
        return type;
    }

    public String getResourceType() {
        return resourceType;
    }

}
