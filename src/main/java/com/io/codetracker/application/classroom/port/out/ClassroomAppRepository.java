package com.io.codetracker.application.classroom.port.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.io.codetracker.domain.classroom.aggregate.ClassroomAggregate;
import com.io.codetracker.domain.classroom.entity.Classroom;
import com.io.codetracker.domain.classroom.entity.ClassroomSettings;

public interface ClassroomAppRepository {
    void save(ClassroomAggregate aggregate);
    void update(ClassroomAggregate aggregate);
    void deleteByClassroomId(UUID classroomId);
    List<ClassroomAggregate> findByInstructorUserId(UUID instructorUserId);
    List<ClassroomAggregate> findAllById(List<UUID> classroomIds);
    Optional<Classroom> findByClassroomId(UUID classroomId);
    Optional<ClassroomSettings> findSettingsByClassroomId(UUID classroomId);
    boolean existsByClassroomId(UUID classroomId);
    boolean existsByClassroomIdAndInstructorUserId(UUID classroomId, UUID instructorUserId);
}
