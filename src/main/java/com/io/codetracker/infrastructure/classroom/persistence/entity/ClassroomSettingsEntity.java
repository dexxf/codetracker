package com.io.codetracker.infrastructure.classroom.persistence.entity;

import com.io.codetracker.common.util.AESEncryptionConverter;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "classroom_settings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassroomSettingsEntity {

    @Id
    @Column(name = "classroom_id")
    private String classroomId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_id", insertable = false, updatable = false)
    private ClassroomEntity classroom;

    @Column(name = "max_students", nullable = false)
    private int maxStudents;

    @Convert(converter = AESEncryptionConverter.class)
    @Column(name = "passcode", length = 255, nullable = true)
    private String passcode;

    @Column(name = "require_approval", nullable = false)
    private boolean requireApproval;

}