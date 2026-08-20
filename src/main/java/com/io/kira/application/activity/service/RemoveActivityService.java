package com.io.kira.application.activity.service;


import java.util.UUID;
import java.util.Optional;

import com.io.kira.application.activity.error.RemoveActivityError;
import com.io.kira.application.activity.port.in.RemoveActivityUseCase;
import com.io.kira.application.activity.result.ActivityDetailsData;
import com.io.kira.common.result.Result;
import org.springframework.stereotype.Service;

import com.io.kira.application.activity.port.out.ActivityAppRepository;
import com.io.kira.application.activity.port.out.ActivityClassroomAppPort;
import com.io.kira.domain.activity.entity.Activity;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public final class RemoveActivityService implements RemoveActivityUseCase {
    
    private final ActivityAppRepository activityAppRepository;
    private final ActivityClassroomAppPort activityClassroomAppPort;

    public Result<ActivityDetailsData, RemoveActivityError> execute(UUID classroomId, UUID activityId, UUID userId) {
      boolean isInstructor = activityClassroomAppPort.existsByClassroomIdAndInstructorUserId(classroomId, userId);

      if(!isInstructor) return Result.fail(RemoveActivityError.USER_NOT_CLASSROOM_INSTRUCTOR);

      Optional<Activity> activity = activityAppRepository.findById(activityId);
      if (activity.isEmpty()) return Result.fail(RemoveActivityError.ACTIVITY_NOT_FOUND);

      activityAppRepository.deleteByActivityId(activityId);
      return Result.ok(ActivityDetailsData.from(activity.get()));
    }
}

