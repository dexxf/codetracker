package com.io.codetracker.application.announcement.port.in;

import com.io.codetracker.application.announcement.command.EditAnnouncementCommand;
import com.io.codetracker.application.announcement.error.EditAnnouncementError;
import com.io.codetracker.application.announcement.result.EditAnnouncementResult;
import com.io.codetracker.common.result.Result;

public interface EditAnnouncementUseCase {
    Result<EditAnnouncementResult, EditAnnouncementError> editAnnouncement(EditAnnouncementCommand command);
}