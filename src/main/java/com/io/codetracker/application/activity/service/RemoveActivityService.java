package com.io.codetracker.application.activity.service;

import java.util.Optional;

import com.io.codetracker.adapter.activity.in.dto.response.ActivityResponse;
import org.springframework.stereotype.Service;

import com.io.codetracker.application.activity.port.out.ActivityAppRepository;
import com.io.codetracker.application.activity.port.out.ActivityClassroomAppPort;
import com.io.codetracker.domain.activity.entity.Activity;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public final class RemoveActivityService {
    
    private final ActivityAppRepository activityAppRepository;
    private final ActivityClassroomAppPort activityClassroomAppPort;

    public ActivityResponse execute(String classroomId, String activityId, String userId) {
      boolean isInstructor = activityClassroomAppPort.existsByClassroomIdAndInstructorUserId(classroomId, userId);

      if(!isInstructor) return ActivityResponse.fail("Instructor is not found in classroomId");

      Optional<Activity> activity = activityAppRepository.findById(activityId);
      if (activity.isEmpty()) return ActivityResponse.fail("Activity is not found.");

      activityAppRepository.deleteByActivityId(activityId);
      return ActivityResponse.success(activity.get(), "Successfully removed activity");
    }
}
