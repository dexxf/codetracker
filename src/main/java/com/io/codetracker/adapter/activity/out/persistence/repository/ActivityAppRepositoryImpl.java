package com.io.codetracker.adapter.activity.out.persistence.repository;

import com.io.codetracker.adapter.activity.out.persistence.mapper.ActivityMapper;
import com.io.codetracker.application.activity.port.out.ActivityAppRepository;
import com.io.codetracker.domain.activity.entity.Activity;
import com.io.codetracker.infrastructure.activity.persistence.entity.ActivityEntity;
import com.io.codetracker.infrastructure.activity.persistence.repository.JpaActivityRepository;
import com.io.codetracker.infrastructure.classroom.persistence.entity.ClassroomEntity;
import com.io.codetracker.infrastructure.classroom.persistence.repository.JpaClassroomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class ActivityAppRepositoryImpl implements ActivityAppRepository {

    private final JpaActivityRepository jpa;
    private final JpaClassroomRepository classroomJpa;

    @Override
    public Activity save(Activity data) {
        ClassroomEntity classroomEntity = classroomJpa.findById(data.getClassroomId())
                .orElseThrow(() -> new RuntimeException("Classroom not found"));

        ActivityEntity entity = ActivityMapper.toEntity(data);
        classroomEntity.addActivity(entity);
        jpa.save(entity);
        return ActivityMapper.toDomain(entity);
    }

    @Override
    public List<Activity> findByClassroomId(String classroomId, String instructorId) {
        return jpa.findByClassroomEntity_ClassroomIdAndClassroomEntity_InstructorUserId(classroomId, instructorId).stream().map(
                ActivityMapper::toDomain
        ).toList();
    }

    @Override
    public Optional<Activity> findById(String activityId) {
        Optional<ActivityEntity> acOptional = jpa.findById(activityId);
        return acOptional.map(ActivityMapper::toDomain);
    }

    @Override
    public void deleteByActivityId(String activityId) {
        jpa.deleteById(activityId);
    }

    @Override
    public void update(Activity updatedActivity) {
        ActivityEntity entity = jpa.findById(updatedActivity.getActivityId())
                .orElseThrow(() -> new RuntimeException("Activity not found"));
        ActivityMapper.updateEntity(updatedActivity, entity);
        jpa.save(entity);
    }
}
