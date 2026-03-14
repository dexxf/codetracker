package com.io.codetracker.application.activity.service;

import java.util.Optional;

import com.io.codetracker.application.activity.error.RemoveActivityError;
import com.io.codetracker.application.activity.port.in.RemoveActivityUseCase;
import com.io.codetracker.application.activity.result.ActivityData;
import com.io.codetracker.common.result.Result;
import org.springframework.stereotype.Service;

import com.io.codetracker.application.activity.port.out.ActivityAppRepository;
import com.io.codetracker.application.activity.port.out.ActivityClassroomAppPort;
import com.io.codetracker.domain.activity.entity.Activity;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public final class RemoveActivityService implements RemoveActivityUseCase {
    
    private final ActivityAppRepository activityAppRepository;
    private final ActivityClassroomAppPort activityClassroomAppPort;

    public Result<ActivityData, RemoveActivityError> execute(String classroomId, String activityId, String userId) {
      boolean isInstructor = activityClassroomAppPort.existsByClassroomIdAndInstructorUserId(classroomId, userId);

      if(!isInstructor) return Result.fail(RemoveActivityError.USER_NOT_CLASSROOM_INSTRUCTOR);

      Optional<Activity> activity = activityAppRepository.findById(activityId);
      if (activity.isEmpty()) return Result.fail(RemoveActivityError.ACTIVITY_NOT_FOUND);

      activityAppRepository.deleteByActivityId(activityId);
      return Result.ok(ActivityData.from(activity.get()));
    }
}
