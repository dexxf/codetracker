package com.io.codetracker.application.announcement.port.in;

import com.io.codetracker.application.announcement.command.DeleteAnnouncementCommand;
import com.io.codetracker.application.announcement.error.DeleteAnnouncementError;
import com.io.codetracker.common.result.Result;

public interface DeleteAnnouncementUseCase {
    Result<Void, DeleteAnnouncementError> deleteAnnouncement(DeleteAnnouncementCommand command);
}
