package com.HE180030.security;

import com.HE180030.security.filter.JWTAuthenticationFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class SecurityConfig {
    JWTAuthenticationFilter filter;
    Logger logger = LoggerFactory.getLogger(this.getClass());
    AuthenticationProvider authenticationProvider;
    String ROLE_ADMIN = "ADMIN";
    String ROLE_SELLER = "SELLER";
    String ROLE_USER = "USER";
    static String[] PUBLIC_ENDPOINTS = {
            "/account/login",
            "/account/verify_email",
            "/account/reset",
            "/account/add",
            "/account/code",
            "/p",
            "/p/last",
            "/cate/",
            "/v3/api-docs/**",
            "/swagger-ui/**",
    };

//    @Bean
//    public SecurityFilterChain authFilterChain1(HttpSecurity http) throws Exception {
//        return http
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/**").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
//                .build();
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .securityMatcher(PUBLIC_ENDPOINTS)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PUBLIC_ENDPOINTS).permitAll()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) ->
                                response.sendRedirect("/account/login"))
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            getError(accessDeniedException);
                            getInfo(request);
                        })
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .maximumSessions(1) // 1 user can access with only 1 device, if he/she try to use with 2 or more, the previous session will immediately be canceled.
                        .expiredUrl("/account/login")
                )
                .httpBasic(Customizer.withDefaults())
                .authenticationProvider(authenticationProvider)
                .build();
    }

    @Bean
    public SecurityFilterChain productFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/p/**")
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/p/edit",
                                "/p/delete",
                                "/p/list"
                        ).hasAnyRole(ROLE_ADMIN, ROLE_SELLER)

                        .requestMatchers(
                                "/p/add",
                                "/p/filter",
                                "/p/search"
                        ).hasAnyRole(ROLE_USER)

                        .requestMatchers(
                                "/p/detail",
                                "/p/load"
                        ).hasAnyRole(
                                ROLE_ADMIN,
                                ROLE_SELLER,
                                ROLE_USER
                        )
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) ->
                                response.sendRedirect("/account/login"))
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            getError(accessDeniedException);
                            getInfo(request);
                        })
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .maximumSessions(1) // 1 user can access with only 1 device, if he/she try to use with 2 or more, the previous session will immediately be canceled.
                        .expiredUrl("/account/login")
                )
                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authenticationProvider)
                .build();
    }

    @Bean
    public SecurityFilterChain invoiceFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/invoice/**")
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/invoice/delete"
                        ).hasAnyRole(ROLE_ADMIN, ROLE_SELLER)

                        .requestMatchers(
                                "/invoice/add_order",
                                "/invoice"
                        ).hasAnyRole(
                                ROLE_ADMIN,
                                ROLE_SELLER,
                                ROLE_USER
                        )
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) ->
                                response.sendRedirect("/account/login"))
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            getError(accessDeniedException);
                            getInfo(request);
                        })
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .maximumSessions(1) // 1 user can access with only 1 device, if he/she try to use with 2 or more, the previous session will immediately be canceled.
                        .expiredUrl("/account/login")
                )
                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authenticationProvider)
                .build();
    }

    @Bean
    public SecurityFilterChain cartFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/c/**")
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/c/manage",
                                "/c",
                                "/c/delete"
                        ).hasAnyRole(ROLE_ADMIN, ROLE_SELLER)

                        .requestMatchers(
                                "/c/add_to_cart",
                                "/c/change_amount",
                                "/c/total_money",
                                "/c/cart",
                                "/c/amount",
                                "/c/add_order"
                        ).hasAnyRole(ROLE_USER)
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) ->
                                response.sendRedirect("/account/login"))
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            getError(accessDeniedException);
                            getInfo(request);
                        })
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .maximumSessions(1) // 1 user can access with only 1 device, if he/she try to use with 2 or more, the previous session will immediately be canceled.
                        .expiredUrl("/account/login")
                )
                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authenticationProvider)
                .build();
    }

    @Bean
    public SecurityFilterChain accountFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/account/**")
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/account/edit_profile",
                                "/account/logout",
                                "/account/refresh"
                        ).hasAnyRole(
                                ROLE_ADMIN,
                                ROLE_SELLER,
                                ROLE_USER
                        )

                        .requestMatchers(
                                "/account/manage",
                                "/account/load",
                                "/account/delete"
                        ).hasAnyRole(ROLE_ADMIN, ROLE_SELLER)
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) ->
                                response.sendRedirect("/account/login"))
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            getError(accessDeniedException);
                            getInfo(request);
                        })
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .maximumSessions(1) // 1 user can access with only 1 device, if he/she try to use with 2 or more, the previous session will immediately be canceled.
                        .expiredUrl("/account/login")
                )
                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authenticationProvider)
                .build();
    }

    @Bean
    public SecurityFilterChain totalTargetSaleFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/sale/**")
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/sale/delete"
                        ).hasAnyRole(
                                ROLE_ADMIN,
                                ROLE_SELLER
                        )
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) ->
                                response.sendRedirect("/account/login"))
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            getError(accessDeniedException);
                            getInfo(request);
                        })
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .maximumSessions(1) // 1 user can access with only 1 device, if he/she try to use with 2 or more, the previous session will immediately be canceled.
                        .expiredUrl("/account/login")
                )
                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authenticationProvider)
                .build();
    }

    @Bean
    public SecurityFilterChain reviewFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/review/**")
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/review/delete"
                        ).hasAnyRole(
                                ROLE_USER,
                                ROLE_ADMIN,
                                ROLE_SELLER
                        )
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) ->
                                response.sendRedirect("/account/login"))
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            getError(accessDeniedException);
                            getInfo(request);
                        })
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .maximumSessions(1) // 1 user can access with only 1 device, if he/she try to use with 2 or more, the previous session will immediately be canceled.
                        .expiredUrl("/account/login")
                )
                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authenticationProvider)
                .build();
    }

    @Bean
    public SecurityFilterChain quantitiesSoldFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/quantity/**")
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/quantity/delete"
                        ).hasAnyRole(ROLE_SELLER)
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) ->
                                response.sendRedirect("/account/login"))
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            getError(accessDeniedException);
                            getInfo(request);
                        })
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .maximumSessions(1) // 1 user can access with only 1 device, if he/she try to use with 2 or more, the previous session will immediately be canceled.
                        .expiredUrl("/account/login")
                )
                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authenticationProvider)
                .build();
    }

    private void getInfo(@NotNull HttpServletRequest request) {
        logger.info("request uri: {}", request.getRequestURI());
    }

    private void getError(@NotNull AccessDeniedException accessDeniedException) {
        logger.error("Error happens when exceptionHandling is working: {}", accessDeniedException.getMessage());
    }
}
