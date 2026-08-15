package com.io.kira.adapter.classroom.out.persistence.repository;

import com.io.kira.adapter.classroom.out.cache.ClassroomCacheNames;
import com.io.kira.domain.activity.valueObject.ActivityStatus;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.io.kira.application.classroom.port.out.ClassroomActivityAppPort;
import com.io.kira.infrastructure.activity.persistence.repository.JpaActivityRepository;

import lombok.AllArgsConstructor;

import java.util.UUID;

@Repository
@AllArgsConstructor
public class ClassroomActivityAppAdapter implements ClassroomActivityAppPort {
   
   private final JpaActivityRepository jpaActivityRepository;

    @Override
   @Cacheable(value = ClassroomCacheNames.CLASSROOM_ACTIVITY, key = "@classroomCacheKey.activityCounts(#classroomId)")
   public long countByClassroomId(UUID classroomId) {
        return jpaActivityRepository.countByClassroomEntity_ClassroomId(classroomId);
    }

    @Override
    @Cacheable(value = ClassroomCacheNames.CLASSROOM_ACTIVITY, key = "@classroomCacheKey.activeActivityCounts(#classroomId)")
    public long countActiveActivitiesByClassroomId(UUID classroomId) {
        return jpaActivityRepository.countByClassroomEntity_ClassroomIdAndStatus(classroomId, ActivityStatus.PUBLISHED);
    }


}
