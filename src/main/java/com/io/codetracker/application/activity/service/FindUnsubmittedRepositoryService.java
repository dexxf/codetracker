package com.io.codetracker.application.activity.service;

import com.io.codetracker.application.activity.command.FindUnsubmittedRepositoryCommand;
import com.io.codetracker.application.activity.error.FindStudentUnsubmittedRepositoryError;
import com.io.codetracker.application.activity.port.in.FindStudentUnsubmittedRepositoryUseCase;
import com.io.codetracker.application.activity.port.out.ActivityClassroomAppPort;
import com.io.codetracker.application.activity.port.out.ActivityClassroomStudentAppPort;
import com.io.codetracker.application.activity.port.out.ActivityGithubSubmissionAppPort;
import com.io.codetracker.application.activity.result.ActivityData;
import com.io.codetracker.common.result.Result;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FindUnsubmittedRepositoryService implements  FindStudentUnsubmittedRepositoryUseCase{

    private final ActivityGithubSubmissionAppPort activityGithubSubmissionAppPort;
    private final ActivityClassroomAppPort activityClassroomAppPort;
    private final ActivityClassroomStudentAppPort activityClassroomStudentAppPort;

    public Result<List<ActivityData>, FindStudentUnsubmittedRepositoryError> execute(FindUnsubmittedRepositoryCommand command) {
        if (!activityClassroomAppPort.existsByClassroomId(command.classroomId())) {
            return Result.fail(FindStudentUnsubmittedRepositoryError.CLASSROOM_NOT_FOUND);
        }

        if(!activityClassroomStudentAppPort.existsByClassroomIdAndStudentUserId(command.classroomId(), command.userId())) {
            return Result.fail(FindStudentUnsubmittedRepositoryError.USER_NOT_CLASSROOM_STUDENT);
        }

        List<ActivityData> listOfSubmittedUser = activityGithubSubmissionAppPort.getUnsubmittedRepositoryActivity(command.classroomId(), command.userId());
        return Result.ok(listOfSubmittedUser);
    }

}
