package com.io.codetracker.application.announcement.result;

import com.io.codetracker.domain.announcement.valueobject.AttachmentType;
import java.util.UUID;

public record AttachmentData(
        UUID attachmentId,
        String url,
        AttachmentType type
) {}