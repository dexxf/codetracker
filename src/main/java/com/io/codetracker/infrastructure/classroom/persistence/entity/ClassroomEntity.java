package com.io.codetracker.infrastructure.classroom.persistence.entity;

import com.io.codetracker.domain.classroom.valueObject.ClassroomStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "classroom")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassroomEntity {

    @Id
    @Column(name = "classroom_id", nullable = false)
    private String classroomId;

    @Column(name = "instructor_user_id", nullable = false)
    private String instructorUserId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", length = 500, nullable = true)
    private String description;

    @Column(name = "class_code", nullable = false, unique = true, length = 30)
    private String classCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 55)
    private ClassroomStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToOne(
            mappedBy = "classroom",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY,
            optional = false
    )
    private ClassroomSettingsEntity settings;

    public void setSettings(ClassroomSettingsEntity settings) {
        this.settings = settings;
        settings.setClassroom(this);
    }
}
