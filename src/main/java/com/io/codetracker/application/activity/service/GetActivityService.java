package com.io.codetracker.application.activity.service;

import com.io.codetracker.application.activity.error.GetActivityError;
import com.io.codetracker.application.activity.port.in.GetActivityUseCase;
import com.io.codetracker.application.activity.port.out.ActivityClassroomAppPort;
import com.io.codetracker.application.activity.command.GetActivityCommand;
import com.io.codetracker.application.activity.port.out.ActivityAppRepository;
import com.io.codetracker.application.activity.result.ActivityData;
import com.io.codetracker.common.result.Result;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class GetActivityService implements GetActivityUseCase {

    private final ActivityAppRepository activityAppRepository;
    private final ActivityClassroomAppPort activityClassroomAppPort;

    public Result<List<ActivityData>, GetActivityError> execute(GetActivityCommand command) {
            if (!activityClassroomAppPort.existsByClassroomId(command.classroomId())) {
                return Result.fail(GetActivityError.CLASSROOM_NOT_FOUND);
            }

            if(!activityClassroomAppPort.existsByClassroomIdAndInstructorUserId(command.classroomId(), command.instructorUserId())){
                return Result.fail(GetActivityError.USER_NOT_CLASSROOM_INSTRUCTOR);
            }

            var activities =  activityAppRepository.findByClassroomId(command.classroomId(), command.instructorUserId())
                    .stream().map(ActivityData::from).toList();

            return Result.ok(activities);
    }

}
