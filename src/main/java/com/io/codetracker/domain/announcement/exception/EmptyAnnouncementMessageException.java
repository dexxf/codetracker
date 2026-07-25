package com.io.codetracker.domain.announcement.exception;

public final class EmptyAnnouncementMessageException extends RuntimeException {

    public EmptyAnnouncementMessageException() {
        super("Announcement message cannot be empty.");
    }
}