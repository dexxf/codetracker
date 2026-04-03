package com.io.codetracker.adapter.auth.in.rest;

import com.io.codetracker.adapter.auth.in.mapper.LogoutHttpMapper;
import com.io.codetracker.application.auth.command.LogoutCommand;
import com.io.codetracker.application.auth.port.in.LogoutUseCase;
import com.io.codetracker.application.auth.result.LogoutResult;
import com.io.codetracker.adapter.auth.out.security.AuthPrincipal;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final LogoutUseCase logoutUseCase;

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

    private final boolean deviceSecure;
    private final boolean deviceHttpOnly;
    private final String devicePath;
    private final String deviceSameSite;
    private final String deviceDomain;

    public AuthController(
            LogoutUseCase logoutUseCase,
            @Value("${app.cookie.jwt.secure}") boolean jwtSecure,
            @Value("${app.cookie.jwt.http-only}") boolean jwtHttpOnly,
            @Value("${app.cookie.jwt.path}") String jwtPath,
            @Value("${app.cookie.jwt.same-site}") String jwtSameSite,
            @Value("${app.cookie.jwt.domain}") String jwtDomain,
            @Value("${app.cookie.refresh.secure}") boolean refreshSecure,
            @Value("${app.cookie.refresh.http-only}") boolean refreshHttpOnly,
            @Value("${app.cookie.refresh.path}") String refreshPath,
            @Value("${app.cookie.refresh.same-site}") String refreshSameSite,
            @Value("${app.cookie.refresh.domain}") String refreshDomain,
            @Value("${app.cookie.device.secure}") boolean deviceSecure,
            @Value("${app.cookie.device.http-only}") boolean deviceHttpOnly,
            @Value("${app.cookie.device.path}") String devicePath,
            @Value("${app.cookie.device.same-site}") String deviceSameSite,
            @Value("${app.cookie.device.domain}") String deviceDomain
    ) {
        this.logoutUseCase = logoutUseCase;
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
        this.deviceSecure = deviceSecure;
        this.deviceHttpOnly = deviceHttpOnly;
        this.devicePath = devicePath;
        this.deviceSameSite = deviceSameSite;
        this.deviceDomain = deviceDomain;
    }

    @GetMapping("/check")
    public ResponseEntity<Map<String, Object>> checkAuthentication(@AuthenticationPrincipal AuthPrincipal principal) {
        if (principal == null) {
            return ResponseEntity.ok(Map.of("authenticated", false));
        }

        return ResponseEntity.ok(Map.of(
                "authenticated", true,
                "authId", principal.getUsername(),
                "roles", principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList(),
                "email", principal.getEmail(),
                "username", principal.getAuthUsername(),
                "fullyInitialized", principal.isFullyInitialized()
        ));
    }

    @PostMapping("/logout/{deviceId}")
    public ResponseEntity<String> logout(
            @PathVariable String deviceId,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        String refreshToken = getCookieValue(request, "refresh_token");
        LogoutResult result = logoutUseCase.execute(new LogoutCommand(deviceId, refreshToken));

        clearCookie(response, "jwt", jwtHttpOnly, jwtSecure, jwtPath, jwtSameSite, jwtDomain);
        clearCookie(response, "refresh_token", refreshHttpOnly, refreshSecure, refreshPath, refreshSameSite, refreshDomain);
        clearCookie(response, "device_id", deviceHttpOnly, deviceSecure, devicePath, deviceSameSite, deviceDomain);

        return ResponseEntity
                .status(LogoutHttpMapper.toStatus(result))
                .body(LogoutHttpMapper.toMessage(result));
    }

    protected static String getCookieValue(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return null;
        for (Cookie c : cookies) {
            if (name.equals(c.getName())) return c.getValue();
        }
        return null;
    }

    private void clearCookie(
            HttpServletResponse response,
            String name,
            boolean httpOnly,
            boolean secure,
            String path,
            String sameSite,
            String domain
    ) {
        ResponseCookie.ResponseCookieBuilder builder = ResponseCookie.from(name, "")
                .httpOnly(httpOnly)
                .secure(secure)
                .path(path)
                .sameSite(sameSite)
                .maxAge(0);

        if (domain != null && !domain.isBlank()) {
            builder.domain(domain);
        }

        response.addHeader("Set-Cookie", builder.build().toString());
    }
}