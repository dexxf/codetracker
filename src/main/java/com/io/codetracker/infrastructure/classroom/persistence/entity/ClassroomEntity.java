package com.io.codetracker.infrastructure.classroom.persistence.entity;


import java.util.UUID;
import com.io.codetracker.domain.classroom.valueObject.ClassroomStatus;
import com.io.codetracker.infrastructure.activity.persistence.entity.ActivityEntity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "classroom")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassroomEntity {

    @Id
    @Column(name = "classroom_id", nullable = false)
    private UUID classroomId;

    @Column(name = "instructor_user_id", nullable = false)
    private UUID instructorUserId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", length = 500, nullable = true)
    private String description;

    @Column(name = "class_code", nullable = false, unique = true, length = 30)
    private String classCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 55)
    private ClassroomStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @OneToOne(
            mappedBy = "classroom",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            optional = false
    )
    private ClassroomSettingsEntity settings;

    @OneToMany(
            mappedBy = "classroom",
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY
    )
    @MapKey(name = "studentUserId")
    private Map<UUID, ClassroomStudentEntity> students = new HashMap<>();

    @OneToMany(
    mappedBy = "classroomEntity",
    cascade = CascadeType.REMOVE,
    fetch = FetchType.LAZY)
    @MapKey(name = "activityId")
    private Map<String, ActivityEntity> activities = new HashMap<>();

    public void setSettings(ClassroomSettingsEntity settings) {
        this.settings = settings;
        settings.setClassroom(this);
    }

    public void addStudent(ClassroomStudentEntity student) {
    students.put(student.getStudentUserId(), student);
    student.setClassroom(this);
        }

    public void addActivity(ActivityEntity activity) {
    activities.put(activity.getActivityId(), activity
            );
    activity.setClassroomEntity(this);
        }

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

