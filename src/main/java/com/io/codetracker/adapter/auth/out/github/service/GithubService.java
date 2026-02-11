package com.io.codetracker.adapter.auth.out.github.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.io.codetracker.adapter.auth.out.github.dto.ExchangeResponse;
import com.io.codetracker.adapter.auth.out.github.dto.GithubEmailDTO;
import com.io.codetracker.adapter.auth.out.github.dto.GithubFetchDataResult;
import com.io.codetracker.adapter.auth.out.github.dto.GithubTokenResult;
import com.io.codetracker.adapter.auth.out.github.dto.GithubUserInfoDTO;

@Service
public final class GithubService {

    private final RestTemplate restTemplate;
    private final String clientId;
    private final String redirectUri;
    private final String clientSecret;

    public GithubService(
            RestTemplate restTemplate,
            @Value("${github.client-id}") String clientId,
            @Value("${github.client-secret}") String clientSecret,
            @Value("${github.redirect-uri}") String redirectUri
    ) {
        this.restTemplate = restTemplate;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
    }
    
    
    public GithubFetchDataResult fetchGithubUser(String accessToken) {

        if (accessToken == null || accessToken.isBlank()) {
            return GithubFetchDataResult.failure("Access token is missing");
        }

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken);
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));
            HttpEntity<Void> req = new HttpEntity<>(headers);

            ResponseEntity<GithubUserInfoDTO> responseEntity = restTemplate.exchange(
                    "https://api.github.com/user",
                    HttpMethod.GET,
                    req,
                    GithubUserInfoDTO.class
            );

            if (!responseEntity.getStatusCode().is2xxSuccessful() || responseEntity.getBody() == null) {
                return GithubFetchDataResult.failure("Failed to fetch GitHub user");
            }

            GithubUserInfoDTO githubUser = responseEntity.getBody();

            if (githubUser.email() == null || githubUser.email().isBlank()) {

                ResponseEntity<GithubEmailDTO[]> emailsResp = restTemplate.exchange(
                        "https://api.github.com/user/emails",
                        HttpMethod.GET,
                        req,
                        GithubEmailDTO[].class
                );

                if (!emailsResp.getStatusCode().is2xxSuccessful() || emailsResp.getBody() == null) {
                    return GithubFetchDataResult.failure("Failed to fetch GitHub emails");
                }

                GithubEmailDTO[] emails = emailsResp.getBody();
                String selected = null;

                for (GithubEmailDTO e : emails) {
                    if (e.primary() && e.verified()) {
                        selected = e.email();
                        break;
                    }
                }

                if (selected == null) {
                    for (GithubEmailDTO e : emails) {
                        if (e.verified()) {
                            selected = e.email();
                            break;
                        }
                    }
                }

                if (selected == null && emails.length > 0) {
                    selected = emails[0].email();
                }

                if (selected == null || selected.isBlank()) {
                    return GithubFetchDataResult.failure("No usable email found");
                }

                githubUser = new GithubUserInfoDTO(
                        githubUser.id(),
                        githubUser.login(),
                        githubUser.repos_url(),
                        githubUser.name(),
                        selected,
                        githubUser.avatar_url()
                );
            }

            return GithubFetchDataResult.success(githubUser);

        } catch (Exception e) {
            return GithubFetchDataResult.failure("GitHub API error: " + e.getMessage());
        }
    }

    public ExchangeResponse exchangeCode(String code) {
        if (code == null || code.isBlank()) {
            return ExchangeResponse.fail("Missing code");
        }

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("client_id", clientId);
        form.add("client_secret", clientSecret);
        form.add("code", code);
        form.add("redirect_uri", redirectUri);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);

        ResponseEntity<GithubTokenResult> response = restTemplate.postForEntity(
                "https://github.com/login/oauth/access_token",
                request,
                GithubTokenResult.class
        );

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            return ExchangeResponse.fail("Token exchange failed.");
        }

        GithubTokenResult tokenResult = response.getBody();
        if (tokenResult.accessToken() == null || tokenResult.accessToken().isBlank()) {
            return ExchangeResponse.fail("No access token received.");
        }

        return ExchangeResponse.ok(tokenResult);
    }

    public String getClientId() {
        return clientId;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

}
