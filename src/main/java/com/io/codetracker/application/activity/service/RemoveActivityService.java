package com.io.codetracker.application.activity.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.io.codetracker.application.activity.port.in.response.RemoveActivityResponse;
import com.io.codetracker.application.activity.port.out.ActivityAppRepository;
import com.io.codetracker.application.activity.port.out.ActivityClassroomAppPort;
import com.io.codetracker.domain.activity.entity.Activity;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public final class RemoveActivityService {
    
    private final ActivityAppRepository activityAppRepository;
    private final ActivityClassroomAppPort activityClassroomAppPort;

    public RemoveActivityResponse execute(String classroomId,String activityId, String userId) {
      boolean isInstructor = activityClassroomAppPort.existsByClassroomIdAndInstructorUserId(classroomId, userId);

      if(!isInstructor) return RemoveActivityResponse.fail("Instructor is not found in classroomId");

      Optional<Activity> activity = activityAppRepository.findById(activityId);
      if (activity.isEmpty()) return RemoveActivityResponse.fail("Activity is not found.");

      activityAppRepository.deleteByActivityId(activityId);
      return RemoveActivityResponse.success(activity.get());
    }
}
