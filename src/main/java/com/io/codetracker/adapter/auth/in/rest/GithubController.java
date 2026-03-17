package com.io.codetracker.adapter.auth.in.rest;

import com.io.codetracker.adapter.auth.in.mapper.GithubOAuthHttpMapper;
import com.io.codetracker.adapter.auth.out.github.result.GithubExchangeResult;
import com.io.codetracker.adapter.auth.out.github.result.GithubUserInfoResult;
import com.io.codetracker.adapter.auth.out.github.service.GithubService;
import com.io.codetracker.adapter.auth.out.security.jwt.JwtService;
import com.io.codetracker.application.auth.command.GithubOAuthLoginCommand;
import com.io.codetracker.application.auth.error.GithubOAuthLoginError;
import com.io.codetracker.application.auth.port.in.GithubOAuthLoginUseCase;
import com.io.codetracker.application.auth.result.GithubOAuthLoginData;
import com.io.codetracker.common.result.Result;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/oauth")
public class GithubController {

        private static final String OAUTH_STATE_KEY = "oauth_state";
        private static final int JWT_COOKIE_MAX_AGE = 60 * 60 * 24;

        private final JwtService jwtService;
        private final GithubService githubService;
        private final GithubOAuthLoginUseCase githubOAuthLoginUseCase;

        private final String scope;
        private final boolean allowSignup;
        private final boolean promptConsent;
        private final String frontendOrigin;


    public GithubController(
            JwtService jwtService,
            GithubService githubService,
                        GithubOAuthLoginUseCase githubOAuthLoginUseCase,
            @Value("${github.scope}") String scope,
            @Value("${github.allow-signup}") boolean allowSignup,
            @Value("${github.prompt-consent}") boolean promptConsent,
            @Value("${app.cors.allowed-origins}") String frontendOrigin
    ) {
        this.jwtService = jwtService;
        this.githubService = githubService;
                this.githubOAuthLoginUseCase = githubOAuthLoginUseCase;
        this.scope = scope;
        this.allowSignup = allowSignup;
        this.promptConsent = promptConsent;
        this.frontendOrigin = frontendOrigin;
    }


        @GetMapping("/github/authorize")
        public ResponseEntity<?> initiateOAuth(HttpSession session) {
        String state = UUID.randomUUID().toString();
                session.setAttribute(OAUTH_STATE_KEY, state);

        String authUrl = "https://github.com/login/oauth/authorize" +
                        "?client_id=" + URLEncoder.encode(githubService.getClientId(), StandardCharsets.UTF_8) +
                        "&redirect_uri=" + URLEncoder.encode(githubService.getRedirectUri(), StandardCharsets.UTF_8) +
                        "&scope=" + URLEncoder.encode(scope, StandardCharsets.UTF_8) +
                        "&state=" + URLEncoder.encode(state, StandardCharsets.UTF_8) +
                        "&allow_signup=" + allowSignup +
                        "&prompt=" + (promptConsent ? "consent" : "none");

        return ResponseEntity.ok(Map.of("state", state, "authUrl", authUrl));
    }


    @GetMapping("/github/callback")
    public ResponseEntity<String> githubCallback(
            @RequestParam("code") String code,
            @RequestParam(value = "state", required = false) String state,
            HttpSession session,
            HttpServletResponse response
    ) {
        if (!isValidState(session, state)) {
            return ResponseEntity.badRequest().body("Invalid state parameter.");
        }

        session.removeAttribute(OAUTH_STATE_KEY);

        GithubExchangeResult accessTokenResult = githubService.exchangeCode(code);
        if (!accessTokenResult.success()) {
            return ResponseEntity.badRequest().body(accessTokenResult.message());
        }

        String accessToken = accessTokenResult.fetchResult().accessToken();
        ResponseEntity<GithubUserInfoResult> githubUserResponse = githubService.fetchGithubUser(accessToken);

        if (!githubUserResponse.getStatusCode().is2xxSuccessful() || githubUserResponse.getBody() == null) {
            return ResponseEntity.badRequest().body("Failed to fetch GitHub user.");
        }

        GithubUserInfoResult githubUser = githubUserResponse.getBody();
        Result<GithubOAuthLoginData, GithubOAuthLoginError> loginResult =
                githubOAuthLoginUseCase.loginOrRegister(
                        new GithubOAuthLoginCommand(
                                githubUser.email(),
                                githubUser.login(),
                                githubUser.id(),
                                accessToken
                        )
                );

        if (!loginResult.success()) {
            return ResponseEntity
                    .status(GithubOAuthHttpMapper.toStatus(loginResult.error()))
                    .body(GithubOAuthHttpMapper.toMessage(loginResult.error()));
        }

        GithubOAuthLoginData loginData = loginResult.data();
        addJwtCookie(response, loginData.authId());

        return ResponseEntity
                .ok()
                .contentType(MediaType.TEXT_HTML)
                .body(buildSuccessHtml(loginData.alreadyRegistered()));
    }

    private boolean isValidState(HttpSession session, String state) {
        String storedState = (String) session.getAttribute(OAUTH_STATE_KEY);
        if (storedState == null) {
            return false;
        }

        return storedState.equals(state);
    }

    private void addJwtCookie(HttpServletResponse response, String authId) {
        String jwtToken = jwtService.generateToken(authId);

        Cookie cookie = new Cookie("jwt", jwtToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(JWT_COOKIE_MAX_AGE);
        response.addCookie(cookie);
    }

    private String buildSuccessHtml(boolean alreadyRegistered) {
        String html = """
        <!DOCTYPE html>
        <html>
        <body>
                <script>
                (function () {
                if (window.opener) {
                window.opener.postMessage(
                        {
                        type: 'OAUTH_RESULT',
                        registered: %b,
                        },
                        '%s'
                );
                }
                window.close();
                })();
                </script>
        </body>
        </html>
        """.formatted(alreadyRegistered, frontendOrigin);

        return html;
    }
}