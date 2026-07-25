package com.io.codetracker.application.announcement.port.out;

import com.io.codetracker.domain.announcement.entity.Announcement;

public interface AnnouncementAppRepository {
    void save(Announcement announcement);
}