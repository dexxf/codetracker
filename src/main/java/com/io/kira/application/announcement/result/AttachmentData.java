package com.io.kira.application.announcement.result;

import com.io.kira.domain.announcement.valueobject.AttachmentType;
import java.util.UUID;

public record AttachmentData(
        UUID attachmentId,
        String url,
        AttachmentType type,
        String resourceType
) {}
