package com.io.codetracker.application.activity.service;

import com.io.codetracker.application.activity.command.EditActivityCommand;
import com.io.codetracker.adapter.activity.in.dto.response.ActivityResponse;
import com.io.codetracker.application.activity.port.out.ActivityAppRepository;
import com.io.codetracker.application.activity.port.out.ActivityClassroomAppPort;
import com.io.codetracker.common.result.Result;
import com.io.codetracker.domain.activity.entity.Activity;
import com.io.codetracker.domain.activity.result.EditActivityResult;
import com.io.codetracker.domain.activity.service.UpdateActivityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EditActivityService {

    private final UpdateActivityService updateActivityService;
    private final ActivityClassroomAppPort activityClassroomAppPort;
    private final ActivityAppRepository activityAppRepository;

    public ActivityResponse execute (EditActivityCommand command) {

        boolean isInstructor = activityClassroomAppPort.existsByClassroomIdAndInstructorUserId(command.classroomId(), command.userId());

        if(!isInstructor) return ActivityResponse.fail("Instructor is not found in classroomId");

        Result<Activity, EditActivityResult> result = updateActivityService.updateAndValidate(command.activityId(), command.title(),
                command.description(), command.dueDate(), command.status(), command.maxScore());

        if (!result.success()) {
            return ActivityResponse.fail(result.error().getMessage());
        }

        Activity updatedActivity = result.data();

        activityAppRepository.save(updatedActivity);
        return ActivityResponse.success(updatedActivity, "Successfully updated Activity.");
    }


}
