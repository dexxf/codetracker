package com.io.codetracker.application.announcement.port.in;

import com.io.codetracker.application.announcement.command.CreateAnnouncementCommand;
import com.io.codetracker.application.announcement.error.CreateAnnouncementError;
import com.io.codetracker.application.announcement.result.CreateAnnouncementResult;
import com.io.codetracker.common.result.Result;

import java.util.UUID;

public interface CreateAnnouncementUseCase {
    Result<CreateAnnouncementResult, CreateAnnouncementError> createAnnouncement(CreateAnnouncementCommand command);
}