package com.io.kira.application.announcement.port.out;

import com.io.kira.domain.announcement.entity.Announcement;

import java.util.Optional;
import java.util.List;
import java.util.UUID;

public interface AnnouncementAppRepository {
    void save(Announcement announcement);

    Optional<Announcement> findById(UUID uuid);

     void deleteById(Announcement announcement);

    List<Announcement> findAllByClassroomId(UUID classroomId);
}