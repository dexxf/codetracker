package com.io.kira.application.announcement.port.in;

import com.io.kira.application.announcement.command.EditAnnouncementCommand;
import com.io.kira.application.announcement.error.EditAnnouncementError;
import com.io.kira.application.announcement.result.EditAnnouncementResult;
import com.io.kira.common.result.Result;

public interface EditAnnouncementUseCase {
    Result<EditAnnouncementResult, EditAnnouncementError> editAnnouncement(EditAnnouncementCommand command);
}