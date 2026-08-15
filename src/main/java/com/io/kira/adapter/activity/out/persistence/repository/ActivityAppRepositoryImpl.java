package com.io.kira.adapter.activity.out.persistence.repository;


import java.util.UUID;
import com.io.kira.adapter.activity.out.cache.ActivityCacheNames;
import com.io.kira.adapter.classroom.out.cache.ClassroomCacheNames;
import com.io.kira.adapter.activity.out.persistence.mapper.ActivityMapper;
import com.io.kira.application.activity.port.out.ActivityAppRepository;
import com.io.kira.application.activity.result.StudentActivityOverviewData;
import com.io.kira.domain.activity.entity.Activity;
import com.io.kira.infrastructure.activity.persistence.entity.ActivityEntity;
import com.io.kira.infrastructure.activity.persistence.repository.JpaActivityRepository;
import com.io.kira.infrastructure.classroom.persistence.entity.ClassroomEntity;
import com.io.kira.infrastructure.classroom.persistence.repository.JpaClassroomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class ActivityAppRepositoryImpl implements ActivityAppRepository {

    private final JpaActivityRepository jpa;
    private final JpaClassroomRepository classroomJpa;

    @Override
    @Caching(evict = {
            @CacheEvict(value = ActivityCacheNames.ACTIVITY, allEntries = true),
            @CacheEvict(value = ActivityCacheNames.ACTIVITY_INFO, allEntries = true),
            @CacheEvict(value = ClassroomCacheNames.CLASSROOM_ACTIVITY,
                    key = "@classroomCacheKey.activityCounts(#data.classroomId)"),
            @CacheEvict(value = ClassroomCacheNames.CLASSROOM_ACTIVITY,
                    key = "@classroomCacheKey.activeActivityCounts(#data.classroomId)"),
            @CacheEvict(value = ClassroomCacheNames.CLASSROOM_RECENT_ACTIVITY, allEntries = true)
    })
    public Activity save(Activity data) {
        ClassroomEntity classroomEntity = classroomJpa.findById(data.getClassroomId())
                .orElseThrow(() -> new RuntimeException("Classroom not found"));


        ActivityEntity entity = ActivityMapper.toEntity(data);
        classroomEntity.addActivity(entity);
        jpa.save(entity);
        return ActivityMapper.toDomain(entity);
    }

    @Override
    @Cacheable(value = ActivityCacheNames.ACTIVITY,
            key = "@activityCacheKey.byClassroomIdAndInstructorUserId(#classroomId, #instructorId)",
            unless = "#result.isEmpty()")
    public List<Activity> findActivitiesByClassroomIdAndInstructorUserId(UUID classroomId, UUID instructorId) {
        return jpa.findByClassroomEntity_ClassroomIdAndClassroomEntity_InstructorUserId(classroomId, instructorId).stream().map(
                ActivityMapper::toDomain
        ).collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = ActivityCacheNames.ACTIVITY,
            key = "@activityCacheKey.byId(#activityId)")
    public Optional<Activity> findById(String activityId) {
        Optional<ActivityEntity> acOptional = jpa.findById(activityId);
        return acOptional.map(ActivityMapper::toDomain);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = ActivityCacheNames.ACTIVITY, allEntries = true),
            @CacheEvict(value = ActivityCacheNames.ACTIVITY_INFO, allEntries = true),
            @CacheEvict(value = ClassroomCacheNames.CLASSROOM_ACTIVITY,
                    key = "@classroomCacheKey.activityCounts(#result.classroomId)"),
            @CacheEvict(value = ClassroomCacheNames.CLASSROOM_ACTIVITY,
                    key = "@classroomCacheKey.activeActivityCounts(#result.classroomId)"),
            @CacheEvict(value = ClassroomCacheNames.CLASSROOM_RECENT_ACTIVITY, allEntries = true)
    })
    public Activity deleteByActivityId(String activityId) {
        ActivityEntity entity = jpa.findById(activityId)
                .orElseThrow(() -> new RuntimeException("Activity not found"));
        Activity activity = ActivityMapper.toDomain(entity);
        jpa.delete(entity);
        return activity;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = ActivityCacheNames.ACTIVITY, allEntries = true),
            @CacheEvict(value = ActivityCacheNames.ACTIVITY_INFO, allEntries = true),
            @CacheEvict(value = ClassroomCacheNames.CLASSROOM_ACTIVITY,
                    key = "@classroomCacheKey.activityCounts(#updatedActivity.classroomId)"),
            @CacheEvict(value = ClassroomCacheNames.CLASSROOM_ACTIVITY,
                    key = "@classroomCacheKey.activeActivityCounts(#updatedActivity.classroomId)"),
            @CacheEvict(value = ClassroomCacheNames.CLASSROOM_RECENT_ACTIVITY, allEntries = true)
    })
    public void update(Activity updatedActivity) {
        ActivityEntity entity = jpa.findById(updatedActivity.getActivityId())
                .orElseThrow(() -> new RuntimeException("Activity not found"));
        ActivityMapper.updateEntity(updatedActivity, entity);
        jpa.save(entity);
    }

    @Override
    @Cacheable(value = ActivityCacheNames.ACTIVITY,
            key = "@activityCacheKey.studentActivitiesByClassroomIdAndUserId(#classroomId, #userId)",
            unless = "#result.isEmpty()")
    public List<StudentActivityOverviewData> findStudentActivities(UUID classroomId, UUID userId) {
        return jpa.findStudentActivityViewsByClassroomIdAndUserId(classroomId, userId);
    }
}

