package com.io.codetracker.application.announcement.command;

import java.util.UUID;

public record ViewAnnouncementsCommand(
        UUID classroomId,
        UUID viewerId
) {
}
