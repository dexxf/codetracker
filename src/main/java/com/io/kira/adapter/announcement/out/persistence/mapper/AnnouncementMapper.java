package com.io.kira.adapter.announcement.out.persistence.mapper;

import com.io.kira.domain.announcement.entity.Announcement;
import com.io.kira.domain.announcement.entity.AnnouncementAttachment;
import com.io.kira.infrastructure.announcement.persistence.entity.AnnouncementEntity;

import java.util.List;

public class AnnouncementMapper {

    public static Announcement toDomain(AnnouncementEntity entity) {
        List<AnnouncementAttachment> attachments = entity.getAttachments()
                .stream()
                .map(AnnouncementAttachmentMapper::toDomain)
                .toList();

        return Announcement.reconstitute(
                entity.getAnnouncementId(),
                entity.getClassroomId(),
                entity.getAuthorId(),
                entity.getMessage(),
                attachments,
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public static AnnouncementEntity toEntity(Announcement announcement) {
        AnnouncementEntity entity = AnnouncementEntity.builder()
                .announcementId(announcement.getAnnouncementId())
                .classroomId(announcement.getClassroomId())
                .authorId(announcement.getAuthorId())
                .message(announcement.getMessage())
                .createdAt(announcement.getCreatedAt())
                .updatedAt(announcement.getUpdatedAt())
                .build();

        announcement.getAttachments()
                .stream()
                .map(AnnouncementAttachmentMapper::toEntity)
                .forEach(entity::addAttachment);

        return entity;
    }

}