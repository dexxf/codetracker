package com.io.codetracker.application.activity.service;

import com.io.codetracker.application.activity.error.GetClassroomOwnerActivityError;
import com.io.codetracker.application.activity.port.in.GetClassroomOwnerActivityUseCase;
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
public class GetActivityService implements GetClassroomOwnerActivityUseCase {

    private final ActivityAppRepository activityAppRepository;
    private final ActivityClassroomAppPort activityClassroomAppPort;

    public Result<List<ActivityData>, GetClassroomOwnerActivityError> execute(GetActivityCommand command) {
            if (!activityClassroomAppPort.existsByClassroomId(command.classroomId())) {
                return Result.fail(GetClassroomOwnerActivityError.CLASSROOM_NOT_FOUND);
            }

            if(!activityClassroomAppPort.existsByClassroomIdAndInstructorUserId(command.classroomId(), command.instructorUserId())){
                return Result.fail(GetClassroomOwnerActivityError.USER_NOT_CLASSROOM_INSTRUCTOR);
            }

            var activities =  activityAppRepository.findByClassroomId(command.classroomId(), command.instructorUserId())
                    .stream().map(ActivityData::from).toList();

            return Result.ok(activities);
    }

}
