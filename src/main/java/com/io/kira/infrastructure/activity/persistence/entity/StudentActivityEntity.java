package com.io.kira.infrastructure.activity.persistence.entity;

import com.io.kira.domain.activity.valueObject.SubmissionStatus;
import com.io.kira.infrastructure.github.persistence.entity.GithubSubmissionEntity;
import com.io.kira.infrastructure.user.persistence.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "student_activity",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "activity_id"})
)
@Getter
@Setter
@NoArgsConstructor
public class StudentActivityEntity {

    @Id
    @Column(name = "student_activity_id")
    private UUID studentActivityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_id", nullable = false)
    private ActivityEntity activityEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    @OneToOne(mappedBy = "studentActivity", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private GithubSubmissionEntity githubSubmission;

    @Enumerated(EnumType.STRING)
    @Column(name = "submission_status", nullable = false)
    private SubmissionStatus submissionStatus;

    @Column(name = "feedback")
    private String feedback;

    @Column(name = "score")
    private Integer score;

    @Column(name = "submitted_commit_sha", length = 40)
    private String submittedCommitSha;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }
}

