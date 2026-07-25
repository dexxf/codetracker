package com.io.codetracker.adapter.announcement.out.persistence.repository;

import com.io.codetracker.adapter.announcement.out.persistence.mapper.AnnouncementMapper;
import com.io.codetracker.application.announcement.port.out.AnnouncementAppRepository;
import com.io.codetracker.domain.announcement.entity.Announcement;
import com.io.codetracker.infrastructure.announcement.persistence.repository.JpaAnnouncementRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class AnnouncementAppRepositoryImpl implements AnnouncementAppRepository {

    private final JpaAnnouncementRepository announcementRepository;

    @Override
    public void save(Announcement announcement) {
        announcementRepository.save(AnnouncementMapper.toEntity(announcement));
    }
}
