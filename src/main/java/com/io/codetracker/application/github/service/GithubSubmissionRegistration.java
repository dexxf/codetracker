package com.io.codetracker.application.github.service;

import com.io.codetracker.application.github.command.CreateGithubSubmissionCommand;
import com.io.codetracker.application.github.error.CreateGithubSubmissionError;
import com.io.codetracker.application.github.port.in.CreateGithubSubmissionUseCase;
import com.io.codetracker.application.github.port.out.GithubSubmissionAppRepository;
import com.io.codetracker.application.github.port.out.GithubSubmissionIntegrationPort;
import com.io.codetracker.application.github.result.GithubRepositoryData;
import com.io.codetracker.application.github.result.GithubSubmissionData;
import com.io.codetracker.common.result.Result;
import com.io.codetracker.domain.github.entity.GithubSubmission;
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
