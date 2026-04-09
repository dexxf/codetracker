package com.io.codetracker.application.activity.service;

import com.io.codetracker.application.activity.error.GetClassroomOwnerActivityError;
import com.io.codetracker.application.activity.error.GetClassroomStudentActivityError;
import com.io.codetracker.application.activity.port.in.GetClassroomOwnerActivityUseCase;
import com.io.codetracker.application.activity.port.in.GetSubmittedActivityUseCase;
import com.io.codetracker.application.activity.port.in.GetClassroomStudentActivityUseCase;
import com.io.codetracker.application.activity.port.out.ActivityClassroomAppPort;
import com.io.codetracker.application.activity.command.GetActivityCommand;
import com.io.codetracker.application.activity.port.out.ActivityAppRepository;
import com.io.codetracker.application.activity.port.out.ActivityClassroomStudentAppPort;
import com.io.codetracker.application.activity.port.out.SubmittedActivityAppRepository;
import com.io.codetracker.application.activity.result.ActivityData;
import com.io.codetracker.application.activity.result.SubmittedActivityData;
import com.io.codetracker.application.activity.result.SubmittedActivityStudentData;
import com.io.codetracker.application.activity.result.SubmittedActivityUserData;
import com.io.codetracker.common.result.Result;
import com.io.codetracker.domain.activity.valueObject.ActivityStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Service
@AllArgsConstructor
public class GetActivityService implements GetClassroomOwnerActivityUseCase, GetClassroomStudentActivityUseCase, GetSubmittedActivityUseCase {

    private final ActivityAppRepository activityAppRepository;
    private final ActivityClassroomAppPort activityClassroomAppPort;
    private final ActivityClassroomStudentAppPort activityClassroomStudentAppPort;
    private final SubmittedActivityAppRepository submittedActivityAppRepository;

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

    @Override
    public Result<Map<String, SubmittedActivityUserData>, GetClassroomOwnerActivityError> execute(GetActivityCommand command) {
        if (!activityClassroomAppPort.existsByClassroomId(command.classroomId())) {
            return Result.fail(GetClassroomOwnerActivityError.CLASSROOM_NOT_FOUND);
        }

        if (!activityClassroomAppPort.existsByClassroomIdAndInstructorUserId(command.classroomId(), command.userId())) {
            return Result.fail(GetClassroomOwnerActivityError.USER_NOT_CLASSROOM_INSTRUCTOR);
        }

        List<SubmittedActivityStudentData> students = submittedActivityAppRepository.findClassroomStudents(command.classroomId());
        List<SubmittedActivityData> submittedActivities = submittedActivityAppRepository.findSubmittedActivities(command.classroomId());

        Map<String, SubmittedActivityUserData> submittedActivityMap = new LinkedHashMap<>();
        for (SubmittedActivityStudentData student : students) {
            submittedActivityMap.put(student.userId(), new SubmittedActivityUserData(
                    student.userId(),
                    student.firstName(),
                    student.lastName(),
                    student.profileUrl(),
                    new ArrayList<>()
            ));
        }

        for (SubmittedActivityData submittedActivity : submittedActivities) {
            SubmittedActivityUserData studentData = submittedActivityMap.get(submittedActivity.userId());
            if (studentData == null) {
                continue;
            }

            studentData.submittedActivities().add(submittedActivity);
        }

        return Result.ok(submittedActivityMap);
    }
}
