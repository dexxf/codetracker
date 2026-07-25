package com.io.codetracker.application.announcement.port.out;

import com.io.codetracker.domain.announcement.valueobject.AttachmentType;

import java.io.IOException;
import java.io.InputStream;

public interface AttachmentTypeResolverPort {
    AttachmentType resolve(InputStream inputStream, String filename) throws IOException;
}