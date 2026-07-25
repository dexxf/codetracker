package com.io.codetracker.domain.announcement.exception;

public final class EmptyAttachmentPublicIdException extends RuntimeException {

    public EmptyAttachmentPublicIdException() {
        super("Attachment publicId must not be empty");
    }
}