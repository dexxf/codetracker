package com.io.codetracker.infrastructure.auth.filter;

import com.io.codetracker.adapter.auth.out.security.CustomUserDetailsService;
import com.io.codetracker.adapter.auth.out.service.JwtService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    private static final String[] PUBLIC_URLS = {
            "/api/oauth/github/authorize",
            "/api/oauth/github/callback",
            "/api/auth/check",
            "/api/auth/refresh/"
    };

    public JwtFilter(JwtService jwtService, CustomUserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (isPublicUrl(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = null;
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }
      else {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("jwt".equals(cookie.getName())) {
                        token = cookie.getValue();
                        break;
                    }
                }
            }
        }   

        if (token != null && !token.isEmpty()) {
            try {
                String authId = jwtService.extractAuthId(token);

                if (authId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
     
                    UserDetails userDetails = userDetailsService.loadUserByUsername(authId);

                    if (jwtService.isTokenValid(token, userDetails)) {
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            } catch (JwtException e) {
                response.setStatus(401);
                return;
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean isPublicUrl(String requestUri) {
        if (requestUri == null) return false;

        String path = requestUri.split("\\?")[0];

        for (String publicUrl : PUBLIC_URLS) {
            if (path.startsWith(publicUrl)) {
                return true;
            }
        }

        return false;
    }
}