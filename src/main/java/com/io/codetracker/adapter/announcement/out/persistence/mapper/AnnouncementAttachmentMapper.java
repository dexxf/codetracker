package com.io.codetracker.adapter.announcement.out.persistence.mapper;

import com.io.codetracker.domain.announcement.entity.AnnouncementAttachment;
import com.io.codetracker.infrastructure.announcement.persistence.entity.AnnouncementAttachmentEntity;

public class AnnouncementAttachmentMapper {

    public static AnnouncementAttachment toDomain(AnnouncementAttachmentEntity entity) {
        return new AnnouncementAttachment(
                entity.getAttachmentId(),
                entity.getUrl(),
                entity.getType()
        );
    }

    public static AnnouncementAttachmentEntity toEntity(AnnouncementAttachment attachment) {
        return AnnouncementAttachmentEntity.builder()
                .attachmentId(attachment.attachmentId())
                .url(attachment.url())
                .type(attachment.type())
                .build();
    }

}