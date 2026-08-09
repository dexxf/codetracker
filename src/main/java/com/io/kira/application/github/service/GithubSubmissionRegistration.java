package com.io.kira.application.github.service;

import com.io.kira.application.github.command.CreateGithubSubmissionCommand;
import com.io.kira.application.github.error.CreateGithubSubmissionError;
import com.io.kira.application.github.port.in.CreateGithubSubmissionUseCase;
import com.io.kira.application.github.port.out.GithubSubmissionAppRepository;
import com.io.kira.application.github.port.out.GithubSubmissionIntegrationPort;
import com.io.kira.application.github.result.GithubRepositoryData;
import com.io.kira.application.github.result.GithubSubmissionData;
import com.io.kira.common.result.Result;
import com.io.kira.domain.github.entity.GithubSubmission;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GithubSubmissionRegistration implements CreateGithubSubmissionUseCase {

    private final GithubSubmissionAppRepository githubSubmissionAppRepository;
    private final GithubSubmissionIntegrationPort githubSubmissionIntegrationPort;

    @Override
    public Result<GithubSubmissionData, CreateGithubSubmissionError> execute(CreateGithubSubmissionCommand command) {
        Optional<GithubRepositoryData> repositoryDataOptional = githubSubmissionIntegrationPort.findByRepository(command.accessToken(), command.repositoryUrl());
        if (repositoryDataOptional.isEmpty()) {
            return Result.fail(CreateGithubSubmissionError.REPOSITORY_NOT_FOUND);
        }

        GithubRepositoryData repositoryData = repositoryDataOptional.get();

        try {
            GithubSubmission githubSubmission = GithubSubmission.createNew(
                    command.classroomId(),
                    command.studentActivityId(),
                    command.activityId(),
                    repositoryData.repositoryOwnerUsername(),
                    repositoryData.repositoryId(),
                    repositoryData.repositoryName(),
                    command.mode(),
                    command.repositoryUrl()
            );

            GithubSubmission savedGithubSubmission = githubSubmissionAppRepository.save(githubSubmission);
            return Result.ok(GithubSubmissionData.from(savedGithubSubmission));
        } catch (RuntimeException e) {
            return Result.fail(CreateGithubSubmissionError.SAVE_FAILED);
        }
    }
}
