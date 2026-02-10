package com.io.codetracker.adapter.auth.in.rest;

import com.io.codetracker.adapter.auth.out.github.dto.ExchangeResponse;
import com.io.codetracker.adapter.auth.out.github.dto.GithubUserInfoDTO;
import com.io.codetracker.adapter.auth.out.github.service.GithubService;
import com.io.codetracker.adapter.auth.out.security.jwt.JwtService;
import com.io.codetracker.application.auth.command.AuthRegisterOAuthCommand;
import com.io.codetracker.application.auth.port.out.GithubAppRepository;
import com.io.codetracker.application.auth.response.AuthRegistrationResponseDTO;
import com.io.codetracker.application.auth.response.GithubRegistrationResponseDTO;
import com.io.codetracker.application.auth.service.AuthRegistration;
import com.io.codetracker.application.auth.service.GithubAccountRegistrationService;
import com.io.codetracker.infrastructure.auth.persistence.entity.GithubAccountEntity;

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
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/oauth")
public class GithubController {

        private final JwtService jwtService;
        private final AuthRegistration registration;
        private final GithubService githubService;
        private final GithubAppRepository githubAppRepository;
        private final GithubAccountRegistrationService ghAccountRegistrationService;
   
        private final String scope;
        private final boolean allowSignup;
        private final boolean promptConsent;
        private final String frontendOrigin;


    public GithubController(
            JwtService jwtService,
            AuthRegistration registration,
            GithubService githubService,
            GithubAppRepository githubAppRepository,
            GithubAccountRegistrationService ghAccountRegistrationService,
            @Value("${github.scope}") String scope,
            @Value("${github.allow-signup}") boolean allowSignup,
            @Value("${github.prompt-consent}") boolean promptConsent,
            @Value("${app.cors.allowed-origins}") String frontendOrigin
    ) {
        this.jwtService = jwtService;
        this.registration = registration;
        this.githubService = githubService;
        this.githubAppRepository = githubAppRepository;
        this.ghAccountRegistrationService = ghAccountRegistrationService;
        this.scope = scope;
        this.allowSignup = allowSignup;
        this.promptConsent = promptConsent;
        this.frontendOrigin = frontendOrigin;
    }

        @GetMapping("/github/authorize")
        public ResponseEntity<?> initiateOAuth(HttpSession session) {
        String state = UUID.randomUUID().toString();
        session.setAttribute("oauth_state", state);

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

        String storedState = (String) session.getAttribute("oauth_state");
        if (storedState == null || !storedState.equals(state)) {
                return ResponseEntity.badRequest().body("Invalid state parameter.");
        }
        session.removeAttribute("oauth_state");

        ExchangeResponse accessTokenResult = githubService.exchangeCode(code);
        if (!accessTokenResult.success()) {
                return ResponseEntity.badRequest().body(accessTokenResult.message());
        }

        String accessToken = accessTokenResult.fetchResult().accessToken();

        ResponseEntity<GithubUserInfoDTO> githubUserResponse =
                githubService.fetchGithubUser(accessToken);

        if (!githubUserResponse.getStatusCode().is2xxSuccessful()
                || githubUserResponse.getBody() == null) {
                return ResponseEntity.badRequest().body("Failed to fetch GitHub user.");
        }

        GithubUserInfoDTO githubUser = githubUserResponse.getBody();

        Optional<GithubAccountEntity> githubAccountEntity =
                githubAppRepository.findByGithubId(githubUser.id());

        boolean alreadyRegistered = githubAccountEntity.isPresent();
        String userAuthId;

        if (alreadyRegistered) {
                userAuthId = githubAccountEntity.get().getAuthId();
        } else {
                AuthRegisterOAuthCommand command =
                        new AuthRegisterOAuthCommand(
                                githubUser.email(),
                                githubUser.login(),
                                "USER"
                        );

                AuthRegistrationResponseDTO registrationResponse =
                        registration.registerWithOAuth(command);
                        
                        if (!registrationResponse.success()) {
                return ResponseEntity.badRequest().body(registrationResponse.message());
                }

                userAuthId = (String) registrationResponse.data().get("authId");
                GithubRegistrationResponseDTO githubRegistrationResponse =
                 ghAccountRegistrationService.registerGithubAccount(userAuthId, githubUser.id(), accessToken);
                
                 if(!githubRegistrationResponse.success()) {
                        return ResponseEntity.badRequest().body(githubRegistrationResponse.message());
                 }
        }

        String jwtToken = jwtService.generateToken(userAuthId);

        Cookie cookie = new Cookie("jwt", jwtToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24);
        response.addCookie(cookie);

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

        return ResponseEntity
                .ok()
                .contentType(MediaType.TEXT_HTML)
                .body(html);
        }

        }