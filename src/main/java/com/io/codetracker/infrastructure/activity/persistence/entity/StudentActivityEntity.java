package com.io.codetracker.infrastructure.activity.persistence.entity;

import com.io.codetracker.domain.activity.valueObject.SubmissionStatus;
import com.io.codetracker.infrastructure.github.persistence.entity.GithubSubmissionEntity;
import com.io.codetracker.infrastructure.user.persistence.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
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
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "student_activity_id")
    private UUID studentActivityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_id", nullable = false)
    private ActivityEntity activityEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    @OneToOne(mappedBy = "studentActivity")
    private GithubSubmissionEntity githubSubmission;

    @Enumerated(EnumType.STRING)
    @Column(name = "submission_status", nullable = false)
    private SubmissionStatus submissionStatus;

    @Column(name = "feedback")
    private String feedback;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
