package com.io.kira.domain.announcement.exception;

import com.io.kira.domain.announcement.valueobject.AttachmentType;

public final class InvalidDisplayImageTypeException extends RuntimeException {

    public InvalidDisplayImageTypeException(AttachmentType type) {
        super("Only IMAGE attachments can be set as the display image, but was: " + type);
    }
}