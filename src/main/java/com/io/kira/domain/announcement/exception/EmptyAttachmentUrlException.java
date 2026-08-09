package com.io.kira.domain.announcement.exception;

public final class EmptyAttachmentUrlException extends RuntimeException {

    public EmptyAttachmentUrlException() {
        super("Attachment url must not be empty");
    }
}