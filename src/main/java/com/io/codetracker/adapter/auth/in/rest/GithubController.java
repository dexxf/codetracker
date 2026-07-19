package com.io.codetracker.adapter.auth.in.rest;

import com.io.codetracker.adapter.auth.in.mapper.GithubOAuthHttpMapper;
import com.io.codetracker.adapter.auth.out.service.JwtService;
import com.io.codetracker.application.auth.command.GithubOAuthSignInCommand;
import com.io.codetracker.application.auth.error.GithubOAuthSignInError;
import com.io.codetracker.application.auth.error.OAuthGithubCallbackError;
import com.io.codetracker.application.auth.port.in.OAuthGithubCallbackUseCase;
import com.io.codetracker.application.auth.port.in.OAuthGithubSignInUseCase;
import com.io.codetracker.application.auth.port.out.OAuthGithubUrlBuilderPort;
import com.io.codetracker.application.auth.result.GithubOAuthSignInData;
import com.io.codetracker.application.auth.result.OAuthGithubCallbackResult;
import com.io.codetracker.common.result.Result;
import com.io.codetracker.infrastructure.auth.config.properties.DeviceIdCookieProperties;
import com.io.codetracker.infrastructure.auth.config.properties.JwtCookieProperties;
import com.io.codetracker.infrastructure.auth.config.properties.RefreshCookieProperties;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.Duration;
import java.util.UUID;

@RestController
@RequestMapping("/api/oauth")
public class GithubController {

    private static final String OAUTH_STATE_KEY = "oauth_state";
    private final JwtService jwtService;
    private final OAuthGithubCallbackUseCase oAuthGithubCallbackUseCase;
    private final OAuthGithubSignInUseCase oAuthGithubSignInUseCase;
    private final int JWT_COOKIE_MAX_AGE_IN_MS;
    private final long REFRESH_TOKEN_MAX_LIFE_TIME_IN_HOUR;
    private final long DEVICE_COOKIE_MAX_AGE_IN_WEEK;

    private final JwtCookieProperties jwtCookieProperties;
    private final RefreshCookieProperties refreshCookieProperties;
    private final DeviceIdCookieProperties deviceIdCookieProperties;

    private final OAuthGithubUrlBuilderPort oAuthGithubUrlBuilderPort;
    private final String frontendOrigin;

    public GithubController(
            JwtService jwtService,
            OAuthGithubCallbackUseCase oAuthGithubCallbackUseCase,
            OAuthGithubSignInUseCase oAuthGithubSignInUseCase,
            OAuthGithubUrlBuilderPort oAuthGithubUrlBuilderPort,
            @Value("${app.cors.allowed-origins}") String frontendOrigin,
            @Value("${jwt.expiration.ms}") int JWT_COOKIE_MAX_AGE_IN_MS,
            @Value("${refresh.token.lifetime.hour}") long REFRESH_TOKEN_MAX_LIFE_TIME_IN_HOUR,
            @Value("${device.expiration.week}") long DEVICE_COOKIE_MAX_AGE_IN_WEEK,
            JwtCookieProperties jwtCookieProperties,
            RefreshCookieProperties refreshCookieProperties,
            DeviceIdCookieProperties deviceIdCookieProperties
    ) {
        this.jwtService = jwtService;
        this.oAuthGithubCallbackUseCase = oAuthGithubCallbackUseCase;
        this.oAuthGithubSignInUseCase = oAuthGithubSignInUseCase;
        this.oAuthGithubUrlBuilderPort = oAuthGithubUrlBuilderPort;
        this.frontendOrigin = frontendOrigin;
        this.JWT_COOKIE_MAX_AGE_IN_MS = JWT_COOKIE_MAX_AGE_IN_MS;
        this.REFRESH_TOKEN_MAX_LIFE_TIME_IN_HOUR = REFRESH_TOKEN_MAX_LIFE_TIME_IN_HOUR;
        this.DEVICE_COOKIE_MAX_AGE_IN_WEEK = DEVICE_COOKIE_MAX_AGE_IN_WEEK;
        this.jwtCookieProperties = jwtCookieProperties;
        this.refreshCookieProperties = refreshCookieProperties;
        this.deviceIdCookieProperties = deviceIdCookieProperties;
    }

    @GetMapping("/github/authorize")
    public ResponseEntity<Void> initiateOAuth(HttpSession session) {
        String state = UUID.randomUUID().toString();
        session.setAttribute(OAUTH_STATE_KEY, state);

        String authUrl = oAuthGithubUrlBuilderPort.buildUrl(state);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(authUrl))
                .build();
    }

    @GetMapping("/github/callback")
    public ResponseEntity<Void> githubCallback(
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "state", required = false) String state,
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "error_description", required = false) String errorDescription,
            HttpSession session,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        if (!isValidState(session, state)) {
            return redirectToFrontend(false, null, "Invalid state parameter.");
        }

        session.removeAttribute(OAUTH_STATE_KEY);

        if (error != null && !error.isBlank()) {
            String message = (errorDescription != null && !errorDescription.isBlank()) ? errorDescription : "GitHub authorization failed.";
            return redirectToFrontend(false, null, message);
        }

        if (code == null || code.isBlank()) {
            return redirectToFrontend(false, null, "Missing OAuth code.");
        }

        Result<OAuthGithubCallbackResult, OAuthGithubCallbackError> callbackResult =
                oAuthGithubCallbackUseCase.handle(code);

        if (!callbackResult.success()) {
            return redirectToFrontend(false, null, GithubOAuthHttpMapper.toMessage(callbackResult.error()));
        }

        OAuthGithubCallbackResult callback = callbackResult.data();

        String userAgent = request.getHeader("User-Agent");
        String deviceId = AuthController.getCookieValue(request, "device_id");

        if (deviceId == null || deviceId.isBlank()) {
            deviceId = UUID.randomUUID().toString();
        }

        if (userAgent == null) userAgent = "Unknown";
        String ipAddress = getClientIp(request);

        Result<GithubOAuthSignInData, GithubOAuthSignInError> loginResult =
                oAuthGithubSignInUseCase.loginOrRegister(
                        new GithubOAuthSignInCommand(
                                callback.userInfoResult().email(),
                                callback.userInfoResult().login(),
                                callback.userInfoResult().id(),
                                callback.tokenResult().accessToken(),
                                deviceId,
                                ipAddress,
                                userAgent
                        )
                );

        if (!loginResult.success()) {
            return redirectToFrontend(false, null, GithubOAuthHttpMapper.toMessage(loginResult.error()));
        }

        GithubOAuthSignInData loginData = loginResult.data();
        addCookie(response, "jwt", jwtService.generateToken(loginData.authId()),
                JWT_COOKIE_MAX_AGE_IN_MS / 1000, jwtCookieProperties.httpOnly(),
                jwtCookieProperties.secure(), jwtCookieProperties.path(), jwtCookieProperties.sameSite(), jwtCookieProperties.domain());

        addCookie(response, "device_id", deviceId,
                Duration.ofDays(DEVICE_COOKIE_MAX_AGE_IN_WEEK * 7).toSeconds(),
                deviceIdCookieProperties.httpOnly(), deviceIdCookieProperties.secure(), deviceIdCookieProperties.path(), deviceIdCookieProperties.sameSite(), deviceIdCookieProperties.domain());

        if (loginData.plainRefreshToken() != null) {
            addCookie(response, "refresh_token", loginData.plainRefreshToken(),
                    REFRESH_TOKEN_MAX_LIFE_TIME_IN_HOUR * 3600, refreshCookieProperties.httpOnly(),
                    refreshCookieProperties.secure(), refreshCookieProperties.path(), refreshCookieProperties.sameSite(), refreshCookieProperties.domain());
        }

        return redirectToFrontend(true, loginData.alreadyRegistered(), null);
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

    private ResponseEntity<Void> redirectToFrontend(boolean success, Boolean alreadyRegistered, String errorMessage) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(frontendOrigin)
                .queryParam("oauth", "github")
                .queryParam("success", success);

        if (alreadyRegistered != null) {
            builder.queryParam("registered", alreadyRegistered);
        }

        if (errorMessage != null && !errorMessage.isBlank()) {
            builder.queryParam("error", errorMessage);
        }

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(builder.build().toUri())
                .build();
    }

    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}