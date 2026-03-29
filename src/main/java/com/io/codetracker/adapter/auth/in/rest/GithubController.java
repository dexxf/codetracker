package com.io.codetracker.adapter.auth.in.rest;

import com.io.codetracker.adapter.auth.in.mapper.GithubOAuthHttpMapper;
import com.io.codetracker.adapter.auth.out.github.result.GithubExchangeResult;
import com.io.codetracker.adapter.auth.out.github.result.GithubUserInfoResult;
import com.io.codetracker.adapter.auth.out.github.service.GithubService;
import com.io.codetracker.adapter.auth.out.service.JwtService;
import com.io.codetracker.application.auth.command.GithubOAuthLoginCommand;
import com.io.codetracker.application.auth.error.GithubOAuthLoginError;
import com.io.codetracker.application.auth.port.in.GithubOAuthLoginUseCase;
import com.io.codetracker.application.auth.result.GithubOAuthLoginData;
import com.io.codetracker.common.result.Result;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/oauth")
public class GithubController {

        private static final String OAUTH_STATE_KEY = "oauth_state";
        private final JwtService jwtService;
        private final GithubService githubService;
        private final GithubOAuthLoginUseCase githubOAuthLoginUseCase;
        private final int JWT_COOKIE_MAX_AGE_IN_MS;
        private final long REFRESH_TOKEN_MAX_LIFE_TIME_IN_HOUR;
        private final long DEVICE_COOKIE_MAX_AGE_IN_WEEK;

        private final boolean jwtSecure;
        private final boolean jwtHttpOnly;
        private final String jwtDomain;
        private final String jwtPath;
        private final String jwtSameSite;
        private final boolean refreshSecure;
        private final boolean refreshHttpOnly;
        private final String refreshDomain;
        private final String refreshPath;
        private final String refreshSameSite;
        private final boolean deviceSecure;
        private final boolean deviceHttpOnly;
        private final String deviceDomain;
        private final String devicePath;
        private final String deviceSameSite;
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
            @Value("${app.cors.allowed-origins}") String frontendOrigin,
            @Value("${jwt.expiration.ms}") int JWT_COOKIE_MAX_AGE_IN_MS,
            @Value("${refresh.token.lifetime.hour}") long REFRESH_TOKEN_MAX_LIFE_TIME_IN_HOUR,
            @Value("${device.expiration.week}") long DEVICE_COOKIE_MAX_AGE_IN_WEEK,
            @Value("${app.cookie.jwt.secure}") boolean jwtSecure,
            @Value("${app.cookie.jwt.http-only}") boolean jwtHttpOnly,
            @Value("${app.cookie.jwt.domain}") String jwtDomain,
            @Value("${app.cookie.jwt.path}") String jwtPath,
            @Value("${app.cookie.jwt.same-site}") String jwtSameSite,
            @Value("${app.cookie.refresh.secure}") boolean refreshSecure,
            @Value("${app.cookie.refresh.http-only}") boolean refreshHttpOnly,
            @Value("${app.cookie.refresh.domain}") String refreshDomain,
            @Value("${app.cookie.refresh.path}") String refreshPath,
            @Value("${app.cookie.refresh.same-site}") String refreshSameSite,
            @Value("${app.cookie.device.secure}") boolean deviceSecure,
            @Value("${app.cookie.device.http-only}") boolean deviceHttpOnly,
            @Value("${app.cookie.device.domain}") String deviceDomain,
            @Value("${app.cookie.device.path}") String devicePath,
            @Value("${app.cookie.device.same-site}") String deviceSameSite
    ) {
        this.jwtService = jwtService;
        this.githubService = githubService;
        this.githubOAuthLoginUseCase = githubOAuthLoginUseCase;
        this.scope = scope;
        this.allowSignup = allowSignup;
        this.promptConsent = promptConsent;
        this.frontendOrigin = frontendOrigin;
        this.JWT_COOKIE_MAX_AGE_IN_MS = JWT_COOKIE_MAX_AGE_IN_MS;
        this.REFRESH_TOKEN_MAX_LIFE_TIME_IN_HOUR = REFRESH_TOKEN_MAX_LIFE_TIME_IN_HOUR;
        this.DEVICE_COOKIE_MAX_AGE_IN_WEEK = DEVICE_COOKIE_MAX_AGE_IN_WEEK;
        this.jwtSecure = jwtSecure;
        this.jwtHttpOnly = jwtHttpOnly;
        this.jwtDomain = jwtDomain;
        this.jwtPath = jwtPath;
        this.jwtSameSite = jwtSameSite;
        this.refreshSecure = refreshSecure;
        this.refreshHttpOnly = refreshHttpOnly;
        this.refreshDomain = refreshDomain;
        this.refreshPath = refreshPath;
        this.refreshSameSite = refreshSameSite;
        this.deviceSecure = deviceSecure;
        this.deviceHttpOnly = deviceHttpOnly;
        this.deviceDomain = deviceDomain;
        this.devicePath = devicePath;
        this.deviceSameSite = deviceSameSite;
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
            HttpServletRequest request,
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
        String userAgent = request.getHeader("User-Agent");
        String deviceId = getCookieValue(request, "device_id");

        if(deviceId == null || deviceId.isBlank()) {
            deviceId = UUID.randomUUID().toString();
        }

        if (userAgent == null) userAgent = "Unknown";
        String ipAddress = getClientIp(request);

        Result<GithubOAuthLoginData, GithubOAuthLoginError> loginResult =
                githubOAuthLoginUseCase.loginOrRegister(
                        new GithubOAuthLoginCommand(
                                githubUser.email(),
                                githubUser.login(),
                                githubUser.id(),
                                accessToken,
                                deviceId,
                                ipAddress,
                                userAgent
                        )                );

        if (!loginResult.success()) {
            return ResponseEntity
                    .status(GithubOAuthHttpMapper.toStatus(loginResult.error()))
                    .body(GithubOAuthHttpMapper.toMessage(loginResult.error()));
        }

        GithubOAuthLoginData loginData = loginResult.data();
        addCookie(response, "jwt", jwtService.generateToken(loginData.authId()),
                JWT_COOKIE_MAX_AGE_IN_MS / 1000, jwtHttpOnly,
                jwtSecure, jwtPath, jwtSameSite, jwtDomain);

        addCookie(response, "device_id", deviceId,
                Duration.ofDays(DEVICE_COOKIE_MAX_AGE_IN_WEEK * 7).toSeconds(),
                deviceHttpOnly, deviceSecure, devicePath, deviceSameSite, deviceDomain);

        if (loginData.plainRefreshToken() != null) {
            addCookie(response, "refresh_token", loginData.plainRefreshToken(),
                    REFRESH_TOKEN_MAX_LIFE_TIME_IN_HOUR * 3600, refreshHttpOnly,
                    refreshSecure, refreshPath, refreshSameSite, refreshDomain);
        }

        return ResponseEntity.ok().contentType(MediaType.TEXT_HTML)
                .body(buildSuccessHtml(loginData.alreadyRegistered()));
    }

    private boolean isValidState(HttpSession session, String state) {
        String storedState = (String) session.getAttribute(OAUTH_STATE_KEY);
        if (storedState == null) {
            return false;
        }

        return storedState.equals(state);
    }

    private void addCookie(HttpServletResponse response, String name, String value, long maxAge, boolean isHttpOnly, boolean isSecure, String path, String sameSite, String domain) {
        ResponseCookie.ResponseCookieBuilder builder = ResponseCookie.from(name, value)
                .httpOnly(isHttpOnly)
                .secure(isSecure)
                .path(path)
                .sameSite(sameSite)
                .maxAge(maxAge);

        if (domain != null && !domain.isBlank()) {
            builder.domain(domain);
        }

        response.addHeader("Set-Cookie", builder.build().toString());
    }

    private String getCookieValue(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return null;
        for (Cookie c : cookies) {
            if (name.equals(c.getName())) return c.getValue();
        }
        return null;
    }

    private String buildSuccessHtml(boolean alreadyRegistered) {
        return """
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
    }

    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}