package com.io.codetracker.adapter.auth.in.rest;

import com.io.codetracker.adapter.auth.in.mapper.LogoutHttpMapper;
import com.io.codetracker.application.auth.command.LogoutCommand;
import com.io.codetracker.application.auth.port.in.LogoutUseCase;
import com.io.codetracker.application.auth.result.LogoutResult;
import com.io.codetracker.adapter.auth.out.security.AuthPrincipal;
import com.io.codetracker.infrastructure.auth.config.properties.DeviceIdCookieProperties;
import com.io.codetracker.infrastructure.auth.config.properties.JwtCookieProperties;
import com.io.codetracker.infrastructure.auth.config.properties.RefreshCookieProperties;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

    private final JwtCookieProperties jwtCookieProperties;
    private final RefreshCookieProperties refreshCookieProperties;
    private final DeviceIdCookieProperties deviceIdCookieProperties;

    public AuthController(
            LogoutUseCase logoutUseCase,
            JwtCookieProperties jwtCookieProperties,
            RefreshCookieProperties refreshCookieProperties,
            DeviceIdCookieProperties deviceIdCookieProperties
    ) {
        this.logoutUseCase = logoutUseCase;
        this.jwtCookieProperties = jwtCookieProperties;
        this.refreshCookieProperties = refreshCookieProperties;
        this.deviceIdCookieProperties = deviceIdCookieProperties;
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

        clearCookie(response, "jwt", jwtCookieProperties.httpOnly(), jwtCookieProperties.secure(),
                jwtCookieProperties.path(), jwtCookieProperties.sameSite(), jwtCookieProperties.domain());
        clearCookie(response, "refresh_token", refreshCookieProperties.httpOnly(), refreshCookieProperties.secure(),
                refreshCookieProperties.path(), refreshCookieProperties.sameSite(), refreshCookieProperties.domain());
        clearCookie(response, "device_id", deviceIdCookieProperties.httpOnly(), deviceIdCookieProperties.secure(),
                deviceIdCookieProperties.path(), deviceIdCookieProperties.sameSite(), deviceIdCookieProperties.domain());

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