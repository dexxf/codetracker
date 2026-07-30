package com.io.codetracker.adapter.announcement.in.mapper;

import com.io.codetracker.application.announcement.error.CreateAnnouncementError;
import com.io.codetracker.application.announcement.error.EditAnnouncementError;
import org.springframework.http.HttpStatus;

public final class AnnouncementHttpMapper {

    private AnnouncementHttpMapper() {
    }

    public static HttpStatus toStatus(CreateAnnouncementError error) {
        return switch (error) {
            case CLASSROOM_NOT_FOUND -> HttpStatus.NOT_FOUND;
            case NOT_CLASSROOM_INSTRUCTOR -> HttpStatus.UNAUTHORIZED;
            default -> HttpStatus.BAD_REQUEST;
        };
    }

    public static String toMessage(CreateAnnouncementError error) {
        return switch (error) {
            case CANT_UPLOAD_FILE -> "Failed to upload announcement attachment.";
            case UNSUPPORTED_FILE_TYPE -> "Announcement attachment type is not supported.";
            case CLASSROOM_NOT_FOUND -> "Classroom not found.";
            case NOT_CLASSROOM_INSTRUCTOR -> "User is not the owner of classroom.";
        };
    }

    public static HttpStatus toStatus(EditAnnouncementError error) {
        return switch (error) {
            case CLASSROOM_NOT_FOUND, ANNOUNCEMENT_NOT_FOUND, ATTACHMENT_NOT_FOUND -> HttpStatus.NOT_FOUND;
            case NOT_CLASSROOM_INSTRUCTOR -> HttpStatus.UNAUTHORIZED;
            default -> HttpStatus.BAD_REQUEST;
        };
    }

    public static String toMessage(EditAnnouncementError error) {
        return switch (error) {
            case CLASSROOM_NOT_FOUND -> "Classroom not found.";
            case ANNOUNCEMENT_NOT_FOUND -> "Announcement not found.";
            case NOT_CLASSROOM_INSTRUCTOR -> "User is not the owner of classroom.";
            case CANT_UPLOAD_FILE -> "Failed to upload announcement attachment.";
            case UNSUPPORTED_FILE_TYPE -> "Announcement attachment type is not supported.";
            case ATTACHMENT_NOT_FOUND -> "Announcement attachment not found.";
            case MESSAGE_TOO_LONG -> "Announcement message is too long.";
        };
    }
}
