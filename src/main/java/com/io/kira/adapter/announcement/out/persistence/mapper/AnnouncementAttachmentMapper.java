package com.io.kira.adapter.announcement.out.persistence.mapper;

import com.io.kira.domain.announcement.entity.AnnouncementAttachment;
import com.io.kira.infrastructure.announcement.persistence.entity.AnnouncementAttachmentEntity;

public class AnnouncementAttachmentMapper {

    public static AnnouncementAttachment toDomain(AnnouncementAttachmentEntity entity) {
        return new AnnouncementAttachment(
                entity.getAttachmentId(),
                entity.getUrl(),
                entity.getType(),
                entity.getResourceType()
        );
    }

    public static AnnouncementAttachmentEntity toEntity(AnnouncementAttachment attachment) {
        return AnnouncementAttachmentEntity.builder()
                .attachmentId(attachment.getAttachmentId())
                .url(attachment.getUrl())
                .type(attachment.getType())
                .resourceType(attachment.getResourceType())
                .build();
    }

}
