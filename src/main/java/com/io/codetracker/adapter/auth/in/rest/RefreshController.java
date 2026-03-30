package com.io.codetracker.adapter.auth.in.rest;

import com.io.codetracker.adapter.auth.in.dto.response.RotateRefreshTokenResponse;
import com.io.codetracker.adapter.auth.in.mapper.RotateRefreshTokenHttpMapper;
import com.io.codetracker.adapter.auth.out.service.JwtService;
import com.io.codetracker.application.auth.command.RotateRefreshTokenCommand;
import com.io.codetracker.application.auth.error.RefreshTokenRotationError;
import com.io.codetracker.application.auth.port.in.RotateRefreshTokenUseCase;
import com.io.codetracker.application.auth.result.RefreshTokenRotationResult;
import com.io.codetracker.common.result.Result;
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

    private final boolean jwtSecure;
    private final boolean jwtHttpOnly;
    private final String jwtPath;
    private final String jwtSameSite;
    private final String jwtDomain;
    private final boolean refreshSecure;
    private final boolean refreshHttpOnly;
    private final String refreshPath;
    private final String refreshSameSite;
    private final String refreshDomain;

    public RefreshController(
            JwtService jwtService,
            RotateRefreshTokenUseCase rotateRefreshTokenUseCase,
            @Value("${jwt.expiration.ms}") int jwtMaxAge,
            @Value("${refresh.token.lifetime.hour}") long refreshTokenMaxAge,
            @Value("${app.cookie.jwt.secure}") boolean jwtSecure,
            @Value("${app.cookie.jwt.http-only}") boolean jwtHttpOnly,
            @Value("${app.cookie.jwt.path}") String jwtPath,
            @Value("${app.cookie.jwt.same-site}") String jwtSameSite,
            @Value("${app.cookie.jwt.domain}") String jwtDomain,
            @Value("${app.cookie.refresh.secure}") boolean refreshSecure,
            @Value("${app.cookie.refresh.http-only}") boolean refreshHttpOnly,
            @Value("${app.cookie.refresh.path}") String refreshPath,
            @Value("${app.cookie.refresh.same-site}") String refreshSameSite,
            @Value("${app.cookie.refresh.domain}") String refreshDomain
    ) {
        this.jwtService = jwtService;
        this.rotateRefreshTokenUseCase = rotateRefreshTokenUseCase;
        this.jwtMaxAge = jwtMaxAge;
        this.refreshTokenMaxAge = refreshTokenMaxAge;

        this.jwtSecure = jwtSecure;
        this.jwtHttpOnly = jwtHttpOnly;
        this.jwtPath = jwtPath;
        this.jwtSameSite = jwtSameSite;
        this.jwtDomain = jwtDomain;
        this.refreshSecure = refreshSecure;
        this.refreshHttpOnly = refreshHttpOnly;
        this.refreshPath = refreshPath;
        this.refreshSameSite = refreshSameSite;
        this.refreshDomain = refreshDomain;
    }
    @PostMapping("/{device_id}")
    public ResponseEntity<RotateRefreshTokenResponse> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response,
            @PathVariable("device_id") String deviceId
    ) {

        if (deviceId == null || deviceId.isBlank() || deviceId.equals("null")) {
            return ResponseEntity.badRequest()
                    .body(RotateRefreshTokenResponse.fail("Device ID is required and must be valid"));
        }

        String plainRefreshToken = extractRefreshTokenFromRequest(request);
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

    private String extractRefreshTokenFromRequest(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("refresh_token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }

    private void addJwtCookie(HttpServletResponse response, String value) {
        ResponseCookie.ResponseCookieBuilder builder = ResponseCookie.from("jwt", value)
                .httpOnly(jwtHttpOnly)
                .secure(jwtSecure)
                .path(jwtPath)
                .sameSite(jwtSameSite)
                .maxAge(jwtMaxAge / 1000);

        if (jwtDomain != null && !jwtDomain.isBlank()) {
            builder.domain(jwtDomain);
        }

        ResponseCookie cookie = builder.build();

        response.addHeader("Set-Cookie", cookie.toString());
    }

    private void addRefreshCookie(HttpServletResponse response, String value) {
        ResponseCookie.ResponseCookieBuilder builder = ResponseCookie.from("refresh_token", value)
                .httpOnly(refreshHttpOnly)
                .secure(refreshSecure)
                .path(refreshPath)
                .sameSite(refreshSameSite)
                .maxAge(refreshTokenMaxAge * 3600);

        if (refreshDomain != null && !refreshDomain.isBlank()) {
            builder.domain(refreshDomain);
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
