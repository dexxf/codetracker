package com.io.codetracker.infrastructure.github.persistence.entity;

import com.io.codetracker.domain.github.valueobject.GithubSubmissionMode;
import com.io.codetracker.infrastructure.activity.persistence.entity.StudentActivityEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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
    private GithubSubmissionMode mode;

    @Column(name = "repository_url", nullable = false)
    private String repositoryUrl;

    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
