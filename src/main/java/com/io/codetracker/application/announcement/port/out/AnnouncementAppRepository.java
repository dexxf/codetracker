package com.io.codetracker.application.announcement.port.out;

import com.io.codetracker.domain.announcement.entity.Announcement;

import java.util.Optional;
import java.util.UUID;

public interface AnnouncementAppRepository {
    void save(Announcement announcement);

    Optional<Announcement> findById(UUID uuid);

    void deleteById(UUID uuid);
}