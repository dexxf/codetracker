package com.io.kira.application.announcement.command;

import java.util.UUID;

public record DeleteAnnouncementCommand(
        UUID announcementId,
        UUID classroomId,
        UUID instructorId
) {
}
