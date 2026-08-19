package com.io.kira.adapter.announcement.out.persistence.mapper;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.io.kira.domain.announcement.entity.Announcement;
import com.io.kira.domain.announcement.entity.AnnouncementAttachment;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public abstract class AnnouncementJacksonMixIn {

    @JsonCreator
    static Announcement reconstitute(
            UUID announcementId,
            UUID classroomId,
            UUID authorId,
            String message,
            List<AnnouncementAttachment> attachments,
            Instant createdAt,
            Instant updatedAt) {
        throw new UnsupportedOperationException();
    }
}
