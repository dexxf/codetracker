package com.io.kira.adapter.announcement.out.persistence.repository;

import com.io.kira.adapter.announcement.out.persistence.mapper.AnnouncementMapper;
import com.io.kira.application.announcement.port.out.AnnouncementAppRepository;
import com.io.kira.domain.announcement.entity.Announcement;
import com.io.kira.infrastructure.announcement.persistence.repository.JpaAnnouncementRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class AnnouncementAppRepositoryImpl implements AnnouncementAppRepository {

    private final JpaAnnouncementRepository announcementRepository;

    @Override
    public void save(Announcement announcement) {
        announcementRepository.save(AnnouncementMapper.toEntity(announcement));
    }

    @Override
    public Optional<Announcement> findById(UUID id) {
        return announcementRepository.findById(id).map(AnnouncementMapper::toDomain);
    }

    @Override
    public void deleteById(UUID id) {
        announcementRepository.deleteById(id);
    }

    @Override
    public List<Announcement> findAllByClassroomId(UUID classroomId) {
        return announcementRepository.findAllByClassroomIdOrderByCreatedAtDesc(classroomId)
                .stream()
                .map(AnnouncementMapper::toDomain)
                .toList();
    }
}
