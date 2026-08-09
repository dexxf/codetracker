package com.io.kira.domain.announcement.exception;

public final class AnnouncementMessageTooLongException extends RuntimeException {

    public AnnouncementMessageTooLongException(int maxLength) {
        super("Announcement message cannot exceed " + maxLength + " characters.");
    }
}