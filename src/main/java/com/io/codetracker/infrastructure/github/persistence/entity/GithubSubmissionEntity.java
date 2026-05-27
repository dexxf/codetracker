package com.io.codetracker.infrastructure.github.persistence.entity;

import com.io.codetracker.domain.github.valueobject.GithubSubmissionMode;
import com.io.codetracker.infrastructure.activity.persistence.entity.StudentActivityEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "github_submission")
@Getter
@Setter
public class GithubSubmissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_activity_id", nullable = false, unique = true)
    private StudentActivityEntity studentActivity;

    @Column(name = "repository_owner_username", nullable = false)
    private String repositoryOwnerUsername;

    @Column(name = "repository_id", nullable = false)
    private String repositoryId;

    @Column(name = "repository_name", nullable = false)
    private String repositoryName;

    @Enumerated(EnumType.STRING)
    @Column(name = "mode", nullable = false)
    private GithubSubmissionMode mode;

    @Column(name = "repository_url", nullable = false)
    private String repositoryUrl;

    @Column(name = "submitted_at")
    private Instant submittedAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }

}
