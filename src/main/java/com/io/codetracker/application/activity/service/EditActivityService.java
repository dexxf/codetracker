package com.io.codetracker.application.activity.service;

import com.io.codetracker.application.activity.command.EditActivityCommand;
import com.io.codetracker.application.activity.error.EditActivityError;
import com.io.codetracker.application.activity.port.in.EditActivityUseCase;
import com.io.codetracker.application.activity.port.out.ActivityAppRepository;
import com.io.codetracker.application.activity.port.out.ActivityClassroomAppPort;
import com.io.codetracker.application.activity.result.ActivityData;
import com.io.codetracker.common.result.Result;
import com.io.codetracker.domain.activity.entity.Activity;
import com.io.codetracker.domain.activity.result.EditActivityResult;
import com.io.codetracker.domain.activity.service.UpdateActivityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EditActivityService implements EditActivityUseCase {

    private final UpdateActivityService updateActivityService;
    private final ActivityClassroomAppPort activityClassroomAppPort;
    private final ActivityAppRepository activityAppRepository;



    public Result<ActivityData, EditActivityError> execute (EditActivityCommand command) {
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
        return Result.ok(ActivityData.from(updatedActivity));
    }


}
