package com.io.kira.application.announcement.port.in;

import com.io.kira.application.announcement.command.CreateAnnouncementCommand;
import com.io.kira.application.announcement.error.CreateAnnouncementError;
import com.io.kira.application.announcement.result.CreateAnnouncementResult;
import com.io.kira.common.result.Result;

public interface CreateAnnouncementUseCase {
    Result<CreateAnnouncementResult, CreateAnnouncementError> createAnnouncement(CreateAnnouncementCommand command);
}