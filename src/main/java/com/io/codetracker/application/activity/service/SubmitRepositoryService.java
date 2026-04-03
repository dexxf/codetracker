package com.io.codetracker.application.activity.service;

import com.io.codetracker.application.activity.error.SubmitExistingRepositoryError;
import com.io.codetracker.application.activity.error.SubmitNewRepositoryError;
import com.io.codetracker.application.activity.port.in.SubmitExistingRepositoryUseCase;
import com.io.codetracker.application.activity.port.in.SubmitNewRepositoryUseCase;
import com.io.codetracker.application.activity.port.out.ActivityClassroomAppPort;
import com.io.codetracker.application.activity.port.out.ActivityGithubAccountAppPort;
import com.io.codetracker.application.activity.port.out.GithubActivityIntegrationPort;
import com.io.codetracker.application.activity.port.out.StudentActivityAppRepository;
import com.io.codetracker.application.activity.result.StudentActivityData;
import com.io.codetracker.common.result.Result;
import com.io.codetracker.domain.activity.entity.StudentActivity;
import com.io.codetracker.domain.auth.entity.GithubAccount;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
public class SubmitRepositoryService implements SubmitNewRepositoryUseCase, SubmitExistingRepositoryUseCase {

    private final StudentActivityAppRepository studentActivityAppRepository;
    private final ActivityClassroomAppPort activityClassroomAppPort;
    private final GithubActivityIntegrationPort githubActivityIntegrationPort;
    private final ActivityGithubAccountAppPort activityGithubAccountAppPort;

    @Override
    public Result<StudentActivityData, SubmitExistingRepositoryError> submitExisting(String authId, String userId, String classroomId, String activityId, String repositoryUrl) {
        if (!activityClassroomAppPort.existsByClassroomId(classroomId))
            return Result.fail(SubmitExistingRepositoryError.CLASSROOM_NOT_FOUND);

        if (!studentActivityAppRepository.existsByUserId(userId))
            return Result.fail(SubmitExistingRepositoryError.USER_NOT_FOUND);

        if (!activityClassroomAppPort.existsByClassroomIdAndActivityId(classroomId, activityId))
            return Result.fail(SubmitExistingRepositoryError.ACTIVITY_NOT_FOUND);

        if (!activityClassroomAppPort.existsByClassroomIdAndStudentUserId(classroomId, userId))
            return Result.fail(SubmitExistingRepositoryError.USER_NOT_CLASSROOM_STUDENT);

        if (studentActivityAppRepository.existsSubmission(userId, activityId))
            return Result.fail(SubmitExistingRepositoryError.ALREADY_SUBMITTED);

        Optional<GithubAccount> githubAccountOptional = activityGithubAccountAppPort.findByAuthId(authId);
        if(githubAccountOptional.isEmpty()) return Result.fail(SubmitExistingRepositoryError.GITHUB_ACCOUNT_NOT_FOUND);
        var  githubAccount = githubAccountOptional.get();


        boolean repositoryExists = githubActivityIntegrationPort.existsByRepository(githubAccount.getAccessToken(),repositoryUrl);

        if (!repositoryExists)
            return Result.fail(SubmitExistingRepositoryError.REPOSITORY_NOT_FOUND);

        try {
            StudentActivity aNew = StudentActivity.createNew(activityId, userId);
            StudentActivity savedStudentActivity = studentActivityAppRepository.save(aNew);
            return Result.ok(StudentActivityData.from(savedStudentActivity));
        } catch (RuntimeException e) {
            return Result.fail(SubmitExistingRepositoryError.SAVE_FAILED);
        }
    }

    @Override
    public Result<StudentActivityData, SubmitNewRepositoryError> submitNew(String authId, String userId, String classroomId, String activityId, String repositoryName) {
        if (!activityClassroomAppPort.existsByClassroomId(classroomId))
            return Result.fail(SubmitNewRepositoryError.CLASSROOM_NOT_FOUND);

        if (!studentActivityAppRepository.existsByUserId(userId))
            return Result.fail(SubmitNewRepositoryError.USER_NOT_FOUND);

        if (!activityClassroomAppPort.existsByClassroomIdAndActivityId(classroomId, activityId))
            return Result.fail(SubmitNewRepositoryError.ACTIVITY_NOT_FOUND);

        if (!activityClassroomAppPort.existsByClassroomIdAndStudentUserId(classroomId, userId))
            return Result.fail(SubmitNewRepositoryError.USER_NOT_CLASSROOM_STUDENT);

        if (studentActivityAppRepository.existsSubmission(userId, activityId))
            return Result.fail(SubmitNewRepositoryError.ALREADY_SUBMITTED);

        Optional<GithubAccount> githubAccountOptional = activityGithubAccountAppPort.findByAuthId(authId);
        if (githubAccountOptional.isEmpty()) return Result.fail(SubmitNewRepositoryError.GITHUB_ACCOUNT_NOT_FOUND);
        var githubAccount = githubAccountOptional.get();

        boolean repositoryExists = githubActivityIntegrationPort.existsByRepositoryName(githubAccount.getAccessToken(), repositoryName);
        if (repositoryExists)
            return Result.fail(SubmitNewRepositoryError.REPOSITORY_ALREADY_EXISTS);

        String createdRepositoryUrl =
                githubActivityIntegrationPort.createRepository(githubAccount.getAccessToken(), repositoryName);

        if (createdRepositoryUrl == null || createdRepositoryUrl.isBlank())
            return Result.fail(SubmitNewRepositoryError.REPOSITORY_CREATE_FAILED);
        try {
            StudentActivity aNew = StudentActivity.createNew(activityId, userId);
            StudentActivity savedStudentActivity = studentActivityAppRepository.save(aNew);
            return Result.ok(StudentActivityData.from(savedStudentActivity));
        } catch (RuntimeException e) {
            return Result.fail(SubmitNewRepositoryError.SAVE_FAILED);
        }
    }

}
