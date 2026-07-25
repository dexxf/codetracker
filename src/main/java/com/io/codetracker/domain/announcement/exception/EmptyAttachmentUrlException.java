package com.io.codetracker.domain.announcement.exception;

public final class EmptyAttachmentUrlException extends RuntimeException {

    public EmptyAttachmentUrlException() {
        super("Attachment url must not be empty");
    }
}