package com.io.codetracker.adapter.github.in.rest;

import com.io.codetracker.adapter.auth.out.security.AuthPrincipal;
import com.io.codetracker.adapter.github.in.dto.response.GetGithubRepositoriesResponse;
import com.io.codetracker.adapter.github.in.mapper.GetGithubRepositoriesHttpMapper;
import com.io.codetracker.application.github.error.GetGithubRepositoriesError;
import com.io.codetracker.application.github.port.in.GetGithubRepositoriesUseCase;
import com.io.codetracker.application.github.result.GithubRepositoryDetailsData;
import com.io.codetracker.common.result.Result;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/github/repositories")
@AllArgsConstructor
public class GithubRepositoryController {

    private final GetGithubRepositoriesUseCase getGithubRepositoriesUseCase;

    @GetMapping
    public ResponseEntity<GetGithubRepositoriesResponse> getGithubRepositories(
            @AuthenticationPrincipal AuthPrincipal authPrincipal
    ) {
        Result<List<GithubRepositoryDetailsData>, GetGithubRepositoriesError> result =
                getGithubRepositoriesUseCase.execute(authPrincipal.getUsername());

        return result.success()
                ? ResponseEntity.ok(GetGithubRepositoriesResponse.ok(result.data()))
                : ResponseEntity.status(GetGithubRepositoriesHttpMapper.toStatus(result.error()))
                .body(GetGithubRepositoriesResponse.fail(GetGithubRepositoriesHttpMapper.toMessage(result.error())));
    }
}
