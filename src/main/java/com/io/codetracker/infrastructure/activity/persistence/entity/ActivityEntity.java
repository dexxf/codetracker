package com.io.codetracker.infrastructure.activity.persistence.entity;

import com.io.codetracker.domain.activity.valueObject.ActivityStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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

    @Column(name = "classroom_id", nullable = false)
    private String classroomId;

    @Column(name = "created_by_professor_id", nullable = false)
    private String createdByProfessorId;

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

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}