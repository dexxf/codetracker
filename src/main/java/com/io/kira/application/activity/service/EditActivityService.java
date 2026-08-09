package com.io.kira.application.activity.service;

import com.io.kira.application.activity.command.EditActivityCommand;
import com.io.kira.application.activity.error.EditActivityError;
import com.io.kira.application.activity.port.in.EditActivityUseCase;
import com.io.kira.application.activity.port.out.ActivityAppRepository;
import com.io.kira.application.activity.port.out.ActivityClassroomAppPort;
import com.io.kira.application.activity.result.ActivityDetailsData;
import com.io.kira.common.result.Result;
import com.io.kira.domain.activity.entity.Activity;
import com.io.kira.domain.activity.result.EditActivityResult;
import com.io.kira.domain.activity.service.UpdateActivityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EditActivityService implements EditActivityUseCase {

    private final UpdateActivityService updateActivityService;
    private final ActivityClassroomAppPort activityClassroomAppPort;
    private final ActivityAppRepository activityAppRepository;

    public Result<ActivityDetailsData, EditActivityError> execute (EditActivityCommand command) {
        boolean classroomExists = activityClassroomAppPort.existsByClassroomId(command.classroomId());
        if(!classroomExists) return Result.fail(EditActivityError.UNKNOWN_CLASSROOM);

        boolean isInstructor = activityClassroomAppPort.existsByClassroomIdAndInstructorUserId(command.classroomId(), command.userId());
        if(!isInstructor) return Result.fail(EditActivityError.NOT_INSTRUCTOR);

        Result<Activity, EditActivityResult> result = updateActivityService.updateAndValidate(command.activityId(), command.title(),
                command.description(), command.dueDate(), command.status(), command.maxScore());

        if (!result.success()) {
            return Result.fail(EditActivityError.from(result.error()));
        }

        Activity updatedActivity = result.data();

        activityAppRepository.update(updatedActivity);
        return Result.ok(ActivityDetailsData.from(updatedActivity));
    }


}
