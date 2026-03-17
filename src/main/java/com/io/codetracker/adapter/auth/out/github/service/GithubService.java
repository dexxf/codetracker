package com.io.codetracker.adapter.auth.out.github.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.io.codetracker.adapter.auth.out.github.dto.GithubExchangeResult;
import com.io.codetracker.adapter.auth.out.github.dto.GithubEmailResult;
import com.io.codetracker.adapter.auth.out.github.dto.GithubTokenResult;
import com.io.codetracker.adapter.auth.out.github.dto.GithubUserInfoResult;

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

    public ResponseEntity<GithubUserInfoResult> fetchGithubUser(String accessToken) {
        if (accessToken == null || accessToken.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<Void> req = new HttpEntity<>(headers);

        ResponseEntity<GithubUserInfoResult> responseEntity = restTemplate.exchange(
                "https://api.github.com/user",
                HttpMethod.GET,
                req,
                GithubUserInfoResult.class
        );

        if (!responseEntity.getStatusCode().is2xxSuccessful() || responseEntity.getBody() == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        GithubUserInfoResult githubUser = responseEntity.getBody();

        if (githubUser.email() == null || githubUser.email().isBlank()) {
            ResponseEntity<GithubEmailResult[]> emailsResp = restTemplate.exchange(
                    "https://api.github.com/user/emails",
                    HttpMethod.GET,
                    req,
                    GithubEmailResult[].class
            );

            if (!emailsResp.getStatusCode().is2xxSuccessful() || emailsResp.getBody() == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }

            GithubEmailResult[] emails = emailsResp.getBody();
            String selected = null;

            for (GithubEmailResult e : emails) {
                if (e.primary() && e.verified()) {
                    selected = e.email();
                    break;
                }
            }

            if (selected == null) {
                for (GithubEmailResult e : emails) {
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
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }

            githubUser = new GithubUserInfoResult(
                    githubUser.id(),
                    githubUser.login(),
                    githubUser.repos_url(),
                    githubUser.name(),
                    selected,
                    githubUser.avatar_url()
            );
        }

        return ResponseEntity.ok(githubUser);
    }

    public GithubExchangeResult exchangeCode(String code) {
        if (code == null || code.isBlank()) {
            return GithubExchangeResult.fail("Missing code");
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
            return GithubExchangeResult.fail("Token exchange failed.");
        }

        GithubTokenResult tokenResult = response.getBody();
        if (tokenResult.accessToken() == null || tokenResult.accessToken().isBlank()) {
            return GithubExchangeResult.fail("No access token received.");
        }

        return GithubExchangeResult.ok(tokenResult);
    }

    public String getClientId() {
        return clientId;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

}
