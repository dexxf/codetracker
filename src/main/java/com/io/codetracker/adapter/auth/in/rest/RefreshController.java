package com.io.codetracker.adapter.auth.in.rest;

import com.io.codetracker.adapter.auth.in.dto.response.RotateRefreshTokenResponse;
import com.io.codetracker.adapter.auth.in.mapper.RotateRefreshTokenHttpMapper;
import com.io.codetracker.adapter.auth.out.service.JwtService;
import com.io.codetracker.application.auth.command.RotateRefreshTokenCommand;
import com.io.codetracker.application.auth.error.RefreshTokenRotationError;
import com.io.codetracker.application.auth.port.in.RotateRefreshTokenUseCase;
import com.io.codetracker.application.auth.result.RefreshTokenRotationResult;
import com.io.codetracker.common.result.Result;
import com.io.codetracker.infrastructure.auth.config.properties.JwtCookieProperties;
import com.io.codetracker.infrastructure.auth.config.properties.RefreshCookieProperties;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/refresh")
public class RefreshController {

    private final JwtService jwtService;
    private final RotateRefreshTokenUseCase rotateRefreshTokenUseCase;

    private final int jwtMaxAge;
    private final long refreshTokenMaxAge;

    private final JwtCookieProperties jwtCookieProperties;
    private final RefreshCookieProperties refreshCookieProperties;

    public RefreshController(
            JwtService jwtService,
            RotateRefreshTokenUseCase rotateRefreshTokenUseCase,
            @Value("${jwt.expiration.ms}") int jwtMaxAge,
            @Value("${refresh.token.lifetime.hour}") long refreshTokenMaxAge,
            JwtCookieProperties jwtCookieProperties,
            RefreshCookieProperties refreshCookieProperties
    ) {
        this.jwtService = jwtService;
        this.rotateRefreshTokenUseCase = rotateRefreshTokenUseCase;
        this.jwtMaxAge = jwtMaxAge;
        this.refreshTokenMaxAge = refreshTokenMaxAge;

        this.jwtCookieProperties = jwtCookieProperties;
        this.refreshCookieProperties = refreshCookieProperties;
    }
    @PostMapping
    public ResponseEntity<RotateRefreshTokenResponse> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        String deviceId = extractCookieFromRequest(request, "device_id");

        if (deviceId == null || deviceId.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(RotateRefreshTokenResponse.fail("Device ID is required and must be valid"));
        }

        String plainRefreshToken = extractCookieFromRequest(request, "refresh_token");
        if (plainRefreshToken == null || plainRefreshToken.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(RotateRefreshTokenResponse.fail("Refresh token is missing"));
        }

        try {
            String ipAddress = getClientIp(request);
            String userAgent = request.getHeader("User-Agent");

            Result<RefreshTokenRotationResult, RefreshTokenRotationError> rotationResult =
                    rotateRefreshTokenUseCase.execute(
                            new RotateRefreshTokenCommand(plainRefreshToken,deviceId,ipAddress,userAgent)
                    );

            if (!rotationResult.success()) {
                return ResponseEntity.status(RotateRefreshTokenHttpMapper.toStatus(rotationResult.error()))
                        .body(RotateRefreshTokenResponse.fail(
                                RotateRefreshTokenHttpMapper.toMessage(rotationResult.error())
                        ));
            }

            RefreshTokenRotationResult result = rotationResult.data();

            String newJwtToken = jwtService.generateToken(result.authId());

            addJwtCookie(response, newJwtToken);
            addRefreshCookie(response, result.plainRefreshToken());

            return ResponseEntity.ok(
                    RotateRefreshTokenResponse.ok(result.expiresAt())
            );

        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(RotateRefreshTokenResponse.fail("Failed to refresh token"));
        }
    }

    private String extractCookieFromRequest(HttpServletRequest request, String cookieName) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }

    private void addJwtCookie(HttpServletResponse response, String value) {
        ResponseCookie.ResponseCookieBuilder builder = ResponseCookie.from("jwt", value)
                .httpOnly(jwtCookieProperties.httpOnly())
                .secure(jwtCookieProperties.secure())
                .path(jwtCookieProperties.path())
                .sameSite(jwtCookieProperties.sameSite())
                .maxAge(jwtMaxAge / 1000);

        if (jwtCookieProperties.domain() != null && !jwtCookieProperties.domain().isBlank()) {
            builder.domain(jwtCookieProperties.domain());
        }

        ResponseCookie cookie = builder.build();

        response.addHeader("Set-Cookie", cookie.toString());
    }

    private void addRefreshCookie(HttpServletResponse response, String value) {
        ResponseCookie.ResponseCookieBuilder builder = ResponseCookie.from("refresh_token", value)
                .httpOnly(refreshCookieProperties.httpOnly())
                .secure(refreshCookieProperties.secure())
                .path(refreshCookieProperties.path())
                .sameSite(refreshCookieProperties.sameSite())
                .maxAge(refreshTokenMaxAge * 3600);

        if (refreshCookieProperties.domain() != null && !refreshCookieProperties.domain().isBlank()) {
            builder.domain(refreshCookieProperties.domain());
        }

        ResponseCookie cookie = builder.build();

        response.addHeader("Set-Cookie", cookie.toString());
    }

    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}