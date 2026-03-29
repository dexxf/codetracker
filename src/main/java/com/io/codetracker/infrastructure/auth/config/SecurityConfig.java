package com.io.codetracker.infrastructure.auth.config;


import com.io.codetracker.adapter.auth.out.security.BCryptPasswordHasher;
import com.io.codetracker.infrastructure.auth.filter.JwtFilter;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        private final UserDetailsService userDetailsService;
        private final JwtFilter jwtFilter;

        public SecurityConfig (UserDetailsService userDetailsService, JwtFilter jwtFilter) {
            this.userDetailsService = userDetailsService;
            this.jwtFilter = jwtFilter;
        }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        return http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .authenticationProvider(authenticationProvider())
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        }))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/oauth/github/authorize",
                                "/api/oauth/github/callback",
                                "/api/auth/check",
                                "/api/auth/refresh/**"
                        ).permitAll()
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public BCryptPasswordHasher passwordEncoder() {
        return new BCryptPasswordHasher(bCryptPasswordEncoder());
    }

    @Bean
    public AuthenticationProvider authenticationProvider () {
        DaoAuthenticationProvider dao = new DaoAuthenticationProvider(userDetailsService);
        dao.setPasswordEncoder(bCryptPasswordEncoder());
        return dao;
    }

}
