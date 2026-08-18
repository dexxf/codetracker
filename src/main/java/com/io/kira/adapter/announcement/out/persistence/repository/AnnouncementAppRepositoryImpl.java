package com.io.kira.adapter.announcement.out.persistence.repository;

import com.io.kira.adapter.announcement.out.cache.AnnouncementCacheNames;
import com.io.kira.adapter.announcement.out.persistence.mapper.AnnouncementMapper;
import com.io.kira.application.announcement.port.out.AnnouncementAppRepository;
import com.io.kira.domain.announcement.entity.Announcement;
import com.io.kira.infrastructure.announcement.persistence.entity.AnnouncementEntity;
import com.io.kira.infrastructure.announcement.persistence.repository.JpaAnnouncementRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class AnnouncementAppRepositoryImpl implements AnnouncementAppRepository {

    private final JpaAnnouncementRepository announcementRepository;

    @Override
    @Caching(evict = {
            @CacheEvict(value = AnnouncementCacheNames.ANNOUNCEMENT,
                    key = "@announcementCacheKey.byId(#announcement.announcementId)"),
            @CacheEvict(value = AnnouncementCacheNames.ANNOUNCEMENT_LIST,
                    key = "@announcementCacheKey.byClassroomId(#announcement.classroomId)")
    })
    public void save(Announcement announcement) {
        announcementRepository.save(AnnouncementMapper.toEntity(announcement));
    }

    @Override
    @Cacheable(
            value = AnnouncementCacheNames.ANNOUNCEMENT,
            key = "@announcementCacheKey.byId(#id)"
    )
    public Optional<Announcement> findById(UUID id) {
        return announcementRepository.findById(id).map(AnnouncementMapper::toDomain);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = AnnouncementCacheNames.ANNOUNCEMENT,
                    key = "@announcementCacheKey.byId(#announcement.announcementId)"),
            @CacheEvict(value = AnnouncementCacheNames.ANNOUNCEMENT_LIST,
            key = "@announcementCacheKey.byClassroomId(#announcement.classroomId)")
    })
    public void deleteById(Announcement announcement) {
        announcementRepository.deleteById(announcement.getAnnouncementId());
    }

    @Override
    @Cacheable(
            value = AnnouncementCacheNames.ANNOUNCEMENT_LIST,
            key = "@announcementCacheKey.byClassroomId(#classroomId)",
            unless = "#result.isEmpty()"
    )
    public List<Announcement> findAllByClassroomId(UUID classroomId) {
        return announcementRepository.findAllByClassroomIdOrderByCreatedAtDesc(classroomId)
                .stream()
                .map(AnnouncementMapper::toDomain)
                .collect(Collectors.toList());
    }
}
