package com.io.codetracker.application.activity.service;

import com.io.codetracker.application.activity.error.GetClassroomOwnerActivityError;
import com.io.codetracker.application.activity.error.GetClassroomStudentActivityError;
import com.io.codetracker.application.activity.port.in.GetClassroomOwnerActivityUseCase;
import com.io.codetracker.application.activity.port.in.GetClassroomStudentActivityUseCase;
import com.io.codetracker.application.activity.port.out.ActivityClassroomAppPort;
import com.io.codetracker.application.activity.command.GetActivityCommand;
import com.io.codetracker.application.activity.port.out.ActivityAppRepository;
import com.io.codetracker.application.activity.port.out.ActivityClassroomStudentAppPort;
import com.io.codetracker.application.activity.result.ActivityData;
import com.io.codetracker.common.result.Result;
import com.io.codetracker.domain.activity.valueObject.ActivityStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class GetActivityService implements GetClassroomOwnerActivityUseCase, GetClassroomStudentActivityUseCase {

    private final ActivityAppRepository activityAppRepository;
    private final ActivityClassroomAppPort activityClassroomAppPort;
    private final ActivityClassroomStudentAppPort activityClassroomStudentAppPort;

    public Result<List<ActivityData>, GetClassroomOwnerActivityError> getOwnerClassroomActivity(GetActivityCommand command) {
            if (!activityClassroomAppPort.existsByClassroomId(command.classroomId())) {
                return Result.fail(GetClassroomOwnerActivityError.CLASSROOM_NOT_FOUND);
            }

            if(!activityClassroomAppPort.existsByClassroomIdAndInstructorUserId(command.classroomId(), command.userId())){
                return Result.fail(GetClassroomOwnerActivityError.USER_NOT_CLASSROOM_INSTRUCTOR);
            }

            List<ActivityData> activities =  activityAppRepository.findActivitiesByClassroomIdAndInstructorUserId(command.classroomId(), command.userId())
                    .stream().map(ActivityData::from).toList();

            return Result.ok(activities);
    }

    @Override
    public Result<List<ActivityData>, GetClassroomStudentActivityError> getStudentClassroomActivity(GetActivityCommand command) {
        if (!activityClassroomAppPort.existsByClassroomId(command.classroomId())) {
            return Result.fail(GetClassroomStudentActivityError.CLASSROOM_NOT_FOUND);
        }

        if(!activityClassroomStudentAppPort.existsByClassroomIdAndStudentUserId(command.classroomId(), command.userId())) {
            return Result.fail(GetClassroomStudentActivityError.USER_NOT_CLASSROOM_STUDENT);
        }

        String classroomOwnerUserId = activityClassroomAppPort.findClassroomOwnerByClassroomId(command.classroomId());
        List<ActivityData> activities = activityAppRepository.findActivitiesByClassroomIdAndInstructorUserId(command.classroomId(), classroomOwnerUserId)
                .stream().filter(e -> e.getStatus() == ActivityStatus.PUBLISHED || e.getStatus() == ActivityStatus.CLOSED).map(ActivityData::from).toList();

        return Result.ok(activities);
    }
}
