package com.io.kira.application.announcement.port.in;

import com.io.kira.application.announcement.command.ViewAnnouncementsCommand;
import com.io.kira.application.announcement.error.ViewAnnouncementsError;
import com.io.kira.application.announcement.result.AnnouncementViewData;
import com.io.kira.common.result.Result;

import java.util.List;

public interface ViewAnnouncementsUseCase {
    Result<List<AnnouncementViewData>, ViewAnnouncementsError> viewAnnouncements(ViewAnnouncementsCommand command);
}
