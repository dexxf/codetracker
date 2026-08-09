package com.io.kira.infrastructure.announcement.persistence.entity;

import com.io.kira.domain.announcement.valueobject.AttachmentType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Getter
@Builder
@Entity
@Table(name = "announcement_attachment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AnnouncementAttachmentEntity {

    @Id
    @Column(name = "attachment_id", nullable = false, updatable = false)
    private UUID attachmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "announcement_id", nullable = false, updatable = false)
    private AnnouncementEntity announcement;

    @Column(name = "url", nullable = false, length = 2048)
    private String url;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 30)
    private AttachmentType type;

    @Column(name = "resource_type", nullable = false, length = 30)
    private String resourceType;

    void assignAnnouncement(AnnouncementEntity announcement) {
        this.announcement = announcement;
    }
}
