package com.io.codetracker.infrastructure.classroom.persistence.entity;

import com.io.codetracker.domain.classroom.valueObject.StudentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "student_classroom")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassroomStudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_user_id", nullable = false)
    private String studentUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_id", nullable = false)
    private ClassroomEntity classroom;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StudentStatus status;

    @Column(name = "last_active_at")
    private LocalDateTime lastActiveAt;

    @Column(name = "joined_at", nullable = false)
    private LocalDateTime joinedAt;

    @Column(name = "left_at", nullable = true)
    private LocalDateTime leftAt;
}