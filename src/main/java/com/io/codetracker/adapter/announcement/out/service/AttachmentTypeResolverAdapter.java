package com.io.codetracker.adapter.announcement.out.service;

import com.io.codetracker.application.announcement.port.out.AttachmentTypeResolverPort;
import com.io.codetracker.domain.announcement.exception.UnsupportedAttachmentTypeException;
import com.io.codetracker.domain.announcement.valueobject.AttachmentType;
import com.io.codetracker.infrastructure.announcement.config.properties.AttachmentProperties;
import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AttachmentTypeResolverAdapter implements AttachmentTypeResolverPort {

    private final Tika tika;
    private final AttachmentProperties properties;

    @Override
    public AttachmentType resolve(InputStream inputStream, String filename) throws UnsupportedAttachmentTypeException, IOException {

        String mimeType = tika.detect(inputStream, filename);

        return properties.supportedTypes()
                .entrySet()
                .stream()
                .filter(entry -> supports(entry.getValue(), mimeType))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow(() -> new UnsupportedAttachmentTypeException(mimeType));
    }

    private boolean supports(
            AttachmentProperties.AttachmentCategory category,
            String mimeType
    ) {
        return category.mimeTypes().contains(mimeType);
    }
}