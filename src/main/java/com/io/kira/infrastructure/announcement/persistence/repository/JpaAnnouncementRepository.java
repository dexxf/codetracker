package com.io.kira.infrastructure.announcement.persistence.repository;

import com.io.kira.infrastructure.announcement.persistence.entity.AnnouncementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import java.util.List;

public interface JpaAnnouncementRepository extends JpaRepository<AnnouncementEntity, UUID> {
    List<AnnouncementEntity> findAllByClassroomIdOrderByCreatedAtDesc(UUID classroomId);
}
