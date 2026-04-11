package com.io.codetracker.infrastructure.activity.persistence.entity;

import com.io.codetracker.domain.activity.valueObject.ActivityStatus;
import com.io.codetracker.infrastructure.classroom.persistence.entity.ClassroomEntity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "activity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityEntity {

    @Id
    @Column(name = "activity_id", nullable = false)
    private String activityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_id", nullable = false)
    private ClassroomEntity classroomEntity;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "description", length = 500, nullable = true)
    private String description;

    @Column(name = "due_date", nullable = true)
    private LocalDateTime dueDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 55)
    private ActivityStatus status;

    @Column(name = "max_score", nullable = true)
    private Integer maxScore;

    @Column(name = "created_at", nullable = false,updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "activityEntity", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<StudentActivityEntity> studentActivities = new HashSet<>();
}