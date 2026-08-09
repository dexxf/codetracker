package com.io.kira.infrastructure.announcement.config.properties;

import com.io.kira.domain.announcement.valueobject.AttachmentType;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;
import java.util.Set;

@ConfigurationProperties(prefix = "cloudinary.announcement.attachment")
public record AttachmentProperties(
        Map<AttachmentType, AttachmentCategory> supportedTypes
) {

    public record AttachmentCategory(
            Set<String> mimeTypes,
            Set<String> extensions
    ) {
    }
}