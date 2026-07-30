package com.io.codetracker.application.announcement.port.in;

import com.io.codetracker.application.announcement.command.ViewAnnouncementsCommand;
import com.io.codetracker.application.announcement.error.ViewAnnouncementsError;
import com.io.codetracker.application.announcement.result.AnnouncementViewData;
import com.io.codetracker.common.result.Result;

import java.util.List;

public interface ViewAnnouncementsUseCase {
    Result<List<AnnouncementViewData>, ViewAnnouncementsError> viewAnnouncements(ViewAnnouncementsCommand command);
}
