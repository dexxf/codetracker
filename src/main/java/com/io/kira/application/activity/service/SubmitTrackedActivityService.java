package com.io.kira.application.activity.service;

import com.io.kira.application.activity.error.SubmitActivityError;
import com.io.kira.application.activity.port.in.SubmitTrackedActivityUseCase;
import com.io.kira.application.activity.port.out.ActivityClassroomAppPort;
import com.io.kira.application.activity.port.out.ActivityGithubAccountAppPort;
import com.io.kira.application.activity.port.out.GithubActivityIntegrationPort;
import com.io.kira.application.activity.port.out.StudentActivityAppRepository;
import com.io.kira.application.activity.result.StudentActivitySubmissionData;
import com.io.kira.common.result.Result;
import com.io.kira.domain.activity.entity.StudentActivity;
import com.io.kira.domain.auth.entity.GithubAccount;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SubmitTrackedActivityService implements SubmitTrackedActivityUseCase {

    private final StudentActivityAppRepository studentActivityAppRepository;
    private final ActivityClassroomAppPort activityClassroomAppPort;
    private final ActivityGithubAccountAppPort activityGithubAccountAppPort;
    private final GithubActivityIntegrationPort githubActivityIntegrationPort;

    @Override
    public Result<StudentActivitySubmissionData, SubmitActivityError> submit(UUID authId, UUID userId, UUID classroomId, String activityId) {
        if (!activityClassroomAppPort.existsByClassroomId(classroomId))
            return Result.fail(SubmitActivityError.CLASSROOM_NOT_FOUND);

        if (!studentActivityAppRepository.existsByUserId(userId))
            return Result.fail(SubmitActivityError.USER_NOT_FOUND);

        if (!activityClassroomAppPort.existsByClassroomIdAndActivityId(classroomId, activityId))
            return Result.fail(SubmitActivityError.ACTIVITY_NOT_FOUND);

        if (!activityClassroomAppPort.existsByClassroomIdAndStudentUserId(classroomId, userId))
            return Result.fail(SubmitActivityError.USER_NOT_CLASSROOM_STUDENT);

        Optional<StudentActivity> studentActivityOptional = studentActivityAppRepository.findByUserIdAndActivityId(userId, activityId);
        if (studentActivityOptional.isEmpty())
            return Result.fail(SubmitActivityError.REPOSITORY_SUBMISSION_NOT_FOUND);

        StudentActivity studentActivity = studentActivityOptional.get();

        Optional<String> repositoryUrl = studentActivityAppRepository.findRepositoryUrlByUserIdAndActivityId(userId, activityId);
        if (repositoryUrl.isEmpty())
            return Result.fail(SubmitActivityError.REPOSITORY_SUBMISSION_NOT_FOUND);

        Optional<GithubAccount> githubAccount = activityGithubAccountAppPort.findByAuthId(authId);
        if (githubAccount.isEmpty())
            return Result.fail(SubmitActivityError.COMMIT_NOT_FOUND);

        Optional<String> commitSha = githubActivityIntegrationPort.findLatestCommitSha(
                githubAccount.get().getAccessToken(), repositoryUrl.get());
        if (commitSha.isEmpty())
            return Result.fail(SubmitActivityError.COMMIT_NOT_FOUND);

        try {
            studentActivity.submit(commitSha.get());
        } catch (IllegalStateException e) {
            return Result.fail(SubmitActivityError.ALREADY_SUBMITTED);
        }

        try {
            StudentActivity savedStudentActivity = studentActivityAppRepository.save(studentActivity);
            return Result.ok(StudentActivitySubmissionData.from(savedStudentActivity));
        } catch (RuntimeException e) {
            return Result.fail(SubmitActivityError.SAVE_FAILED);
        }
    }
}
