package com.io.codetracker.application.activity.service;

import com.io.codetracker.application.activity.command.AddActivityCommand;
import com.io.codetracker.application.activity.port.in.AddActivityUseCase;
import com.io.codetracker.application.activity.port.out.ActivityAppRepository;
import com.io.codetracker.application.activity.port.out.ActivityClassroomAppPort;
import com.io.codetracker.application.activity.result.ActivityData;
import com.io.codetracker.common.result.Result;
import com.io.codetracker.domain.activity.entity.Activity;
import com.io.codetracker.domain.activity.result.ActivityCreationResult;
import com.io.codetracker.domain.activity.service.ActivityCreationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddActivityService implements AddActivityUseCase {

    private final ActivityAppRepository activityAppRepository;
    private final ActivityCreationService activityCreationService;
    private final ActivityClassroomAppPort activityClassroomAppPort;

    public Result<ActivityData, String> execute(AddActivityCommand command) {
        boolean classroomExists = activityClassroomAppPort.existsByClassroomId(command.classroomId());
        if(!classroomExists)
            return Result.fail("Classroom does not exists");

        boolean isOwner = activityClassroomAppPort.existsByClassroomIdAndInstructorUserId(command.classroomId(),command.instructorUserId());
        if(!isOwner)
            return Result.fail("User is not the owner of classroom");

        Result<Activity, ActivityCreationResult> activityCreationRes = activityCreationService.create(
                command.classroomId(), command.instructorUserId(), command.title(),
                command.description(), command.dueDate(), command.maxScore(), command.status());

        if(!activityCreationRes.success())
            return Result.fail(activityCreationRes.error().getMessage());

        Activity saveRes = activityAppRepository.save(activityCreationRes.data());

        if(saveRes == null) return Result.fail("Saving failed.");

        return Result.ok(ActivityData.from(saveRes));
    }

}