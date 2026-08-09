package com.io.kira.application.announcement.port.in;

import com.io.kira.application.announcement.command.DeleteAnnouncementCommand;
import com.io.kira.application.announcement.error.DeleteAnnouncementError;
import com.io.kira.common.result.Result;

public interface DeleteAnnouncementUseCase {
    Result<Void, DeleteAnnouncementError> deleteAnnouncement(DeleteAnnouncementCommand command);
}
