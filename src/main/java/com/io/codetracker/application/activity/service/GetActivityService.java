package com.io.codetracker.application.activity.service;


import java.util.UUID;
import com.io.codetracker.application.activity.error.GetClassroomOwnerActivityError;
import com.io.codetracker.application.activity.error.GetClassroomStudentActivityError;
import com.io.codetracker.application.activity.port.in.GetClassroomOwnerActivityUseCase;
import com.io.codetracker.application.activity.port.in.GetStudentActivityInfoUseCase;
import com.io.codetracker.application.activity.port.in.GetClassroomStudentActivityUseCase;
import com.io.codetracker.application.activity.port.out.ActivityClassroomAppPort;
import com.io.codetracker.application.activity.command.GetActivityCommand;
import com.io.codetracker.application.activity.port.out.ActivityAppRepository;
import com.io.codetracker.application.activity.port.out.ActivityClassroomStudentAppPort;
import com.io.codetracker.application.activity.port.out.StudentActivityInfoAppRepository;
import com.io.codetracker.application.activity.result.ActivityDetailsData;
import com.io.codetracker.application.activity.result.StudentSubmissionDetailsData;
import com.io.codetracker.application.activity.result.StudentSummaryData;
import com.io.codetracker.application.activity.result.StudentActivitySummaryData;
import com.io.codetracker.application.activity.result.StudentActivityOverviewData;
import com.io.codetracker.common.result.Result;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Service
@AllArgsConstructor
public class GetActivityService implements GetClassroomOwnerActivityUseCase, GetClassroomStudentActivityUseCase, GetStudentActivityInfoUseCase {

    private final ActivityAppRepository activityAppRepository;
    private final ActivityClassroomAppPort activityClassroomAppPort;
    private final ActivityClassroomStudentAppPort activityClassroomStudentAppPort;
    private final StudentActivityInfoAppRepository studentActivityInfoAppRepository;

    public Result<List<ActivityDetailsData>, GetClassroomOwnerActivityError> getOwnerClassroomActivity(GetActivityCommand command) {
            if (!activityClassroomAppPort.existsByClassroomId(command.classroomId())) {
                return Result.fail(GetClassroomOwnerActivityError.CLASSROOM_NOT_FOUND);
            }

            if(!activityClassroomAppPort.existsByClassroomIdAndInstructorUserId(command.classroomId(), command.userId())){
                return Result.fail(GetClassroomOwnerActivityError.USER_NOT_CLASSROOM_INSTRUCTOR);
            }

            List<ActivityDetailsData> activities =  activityAppRepository.findActivitiesByClassroomIdAndInstructorUserId(command.classroomId(), command.userId())
                    .stream().map(ActivityDetailsData::from).toList();

            return Result.ok(activities);
    }

    @Override
    public Result<List<StudentActivityOverviewData>, GetClassroomStudentActivityError> getStudentClassroomActivity(GetActivityCommand command) {
        if (!activityClassroomAppPort.existsByClassroomId(command.classroomId())) {
            return Result.fail(GetClassroomStudentActivityError.CLASSROOM_NOT_FOUND);
        }

        if(!activityClassroomStudentAppPort.existsByClassroomIdAndStudentUserId(command.classroomId(), command.userId())) {
            return Result.fail(GetClassroomStudentActivityError.USER_NOT_CLASSROOM_STUDENT);
        }

        List<StudentActivityOverviewData> activities = activityAppRepository.findStudentActivities(command.classroomId(), command.userId());

        return Result.ok(activities);
    }

    @Override
    public Result<Map<UUID, StudentActivitySummaryData>, GetClassroomOwnerActivityError> execute(GetActivityCommand command) {
        if (!activityClassroomAppPort.existsByClassroomId(command.classroomId())) {
            return Result.fail(GetClassroomOwnerActivityError.CLASSROOM_NOT_FOUND);
        }

        if (!activityClassroomAppPort.existsByClassroomIdAndInstructorUserId(command.classroomId(), command.userId())) {
            return Result.fail(GetClassroomOwnerActivityError.USER_NOT_CLASSROOM_INSTRUCTOR);
        }

        List<StudentSummaryData> students = studentActivityInfoAppRepository.findClassroomStudents(command.classroomId());
        List<StudentSubmissionDetailsData> studentActivities = studentActivityInfoAppRepository.findStudentActivityInfos(command.classroomId());

        Map<UUID, StudentActivitySummaryData> studentActivityInfoMap = new LinkedHashMap<>();
        for (StudentSummaryData student : students) {
            studentActivityInfoMap.put(student.userId(), new StudentActivitySummaryData(
                    student.userId(),
                    student.firstName(),
                    student.lastName(),
                    student.profileUrl(),
                    new ArrayList<>()
            ));
        }

        for (StudentSubmissionDetailsData studentActivity : studentActivities) {
            StudentActivitySummaryData studentData = studentActivityInfoMap.get(studentActivity.userId());
            if (studentData == null) {
                continue;
            }

            studentData.studentActivities().add(studentActivity);
        }

        return Result.ok(studentActivityInfoMap);
    }
}

