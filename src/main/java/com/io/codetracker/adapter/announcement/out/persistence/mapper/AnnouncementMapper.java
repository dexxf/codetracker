package com.io.codetracker.adapter.announcement.out.persistence.mapper;

import com.io.codetracker.domain.announcement.entity.Announcement;
import com.io.codetracker.domain.announcement.entity.AnnouncementAttachment;
import com.io.codetracker.infrastructure.announcement.persistence.entity.AnnouncementEntity;

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
                .announcementId(announcement.announcementId())
                .classroomId(announcement.classroomId())
                .authorId(announcement.authorId())
                .message(announcement.message())
                .createdAt(announcement.createdAt())
                .updatedAt(announcement.updatedAt())
                .build();

        announcement.attachments()
                .stream()
                .map(AnnouncementAttachmentMapper::toEntity)
                .forEach(entity::addAttachment);

        return entity;
    }

}