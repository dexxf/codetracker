package com.io.codetracker.application.activity.service;

import com.io.codetracker.application.activity.command.AddActivityCommand;
import com.io.codetracker.application.activity.port.in.response.AddActivityResponse;
import com.io.codetracker.application.activity.port.out.ActivityAppRepository;
import com.io.codetracker.common.result.Result;
import com.io.codetracker.domain.activity.entity.Activity;
import com.io.codetracker.domain.activity.result.ActivityCreationResult;
import com.io.codetracker.domain.activity.service.ActivityCreationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddActivityService {

    private final ActivityAppRepository activityAppRepository;
    private final ActivityCreationService activityCreationService;

    public AddActivityResponse execute(AddActivityCommand command) {

        Result<Activity, ActivityCreationResult> activityCreationRes = activityCreationService.create(
                command.classroomId(), command.instructorUserId(), command.title(),
                command.description(), command.dueDate(), command.maxScore(), command.status());

        if(!activityCreationRes.success())
            return AddActivityResponse.fail(activityCreationRes.error().getMessage());

        var saveRes = activityAppRepository.save(activityCreationRes.data());

        if(saveRes == null) return AddActivityResponse.fail("Saving failed.");

        return AddActivityResponse.success(saveRes);
    }

}