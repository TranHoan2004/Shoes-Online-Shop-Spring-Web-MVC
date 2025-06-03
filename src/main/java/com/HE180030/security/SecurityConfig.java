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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
//@EnableWebSecurity
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class SecurityConfig {
    JWTAuthenticationFilter filter;
    Logger logger = LoggerFactory.getLogger(this.getClass());
    String ROLE_ADMIN = "ADMIN";
    String ROLE_SELLER = "SELLER";
    String ROLE_USER = "USER";

    @Bean
    public SecurityFilterChain authFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

//    @Bean
//    public SecurityFilterChain authFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .securityMatcher("/account/login", "/account/logout", "/account/refresh")
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/account/login").permitAll()
//                        .requestMatchers("/account/logout", "/account/refresh").hasAnyRole(
//                                ROLE_ADMIN,
//                                ROLE_SELLER,
//                                ROLE_USER
//                        )
//                        .anyRequest().authenticated()
//                )
//                .exceptionHandling(ex -> ex
//                        .authenticationEntryPoint((request, response, authException) ->
//                                response.sendRedirect("/account/login"))
//                        .accessDeniedHandler((request, response, accessDeniedException) -> {
//                            getError(accessDeniedException);
//                            getInfo(request);
//                        })
//                )
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//                        .maximumSessions(1) // 1 user can access with only 1 device, if he/she try to use with 2 or more, the previous session will immediately be canceled.
//                        .expiredUrl("/account/login")
//                )
//                .httpBasic(Customizer.withDefaults())
//                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
//                .build();
//    }
//
//    @Bean
//    public SecurityFilterChain productFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .securityMatcher("/product/**")
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(
//                                "/product",
//                                "/product/last"
//                        ).permitAll()
//
//                        .requestMatchers(
//                                "/product/edit",
//                                "/product/delete",
//                                "/product/list"
//                        ).hasAnyRole(ROLE_ADMIN, ROLE_SELLER)
//
//                        .requestMatchers(
//                                "/product/add",
//                                "/product/filter",
//                                "/product/search"
//                        ).hasAnyRole(ROLE_USER)
//
//                        .requestMatchers(
//                                "/product/detail",
//                                "/product/load"
//                        ).hasAnyRole(
//                                ROLE_ADMIN,
//                                ROLE_SELLER,
//                                ROLE_USER
//                        )
//                        .anyRequest().authenticated()
//                )
//                .exceptionHandling(ex -> ex
//                        .authenticationEntryPoint((request, response, authException) ->
//                                response.sendRedirect("/account/login"))
//                        .accessDeniedHandler((request, response, accessDeniedException) -> {
//                            getError(accessDeniedException);
//                            getInfo(request);
//                        })
//                )
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//                        .maximumSessions(1) // 1 user can access with only 1 device, if he/she try to use with 2 or more, the previous session will immediately be canceled.
//                        .expiredUrl("/account/login")
//                )
//                .httpBasic(Customizer.withDefaults())
//                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
//                .build();
//    }
//
//    @Bean
//    public SecurityFilterChain invoiceFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .securityMatcher("/invoice/**")
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(
//                                "/invoice/delete"
//                        ).hasAnyRole(ROLE_ADMIN, ROLE_SELLER)
//
//                        .requestMatchers(
//                                "/invoice/add_order",
//                                "/invoice"
//                        ).hasAnyRole(
//                                ROLE_ADMIN,
//                                ROLE_SELLER,
//                                ROLE_USER
//                        )
//                        .anyRequest().authenticated()
//                )
//                .exceptionHandling(ex -> ex
//                        .authenticationEntryPoint((request, response, authException) ->
//                                response.sendRedirect("/account/login"))
//                        .accessDeniedHandler((request, response, accessDeniedException) -> {
//                            getError(accessDeniedException);
//                            getInfo(request);
//                        })
//                )
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//                        .maximumSessions(1) // 1 user can access with only 1 device, if he/she try to use with 2 or more, the previous session will immediately be canceled.
//                        .expiredUrl("/account/login")
//                )
//                .httpBasic(Customizer.withDefaults())
//                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
//                .build();
//    }
//
//    @Bean
//    public SecurityFilterChain cartFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .securityMatcher("/cart/**")
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(
//                                "/cart/manage",
//                                "/cart",
//                                "/cart/delete"
//                        ).hasAnyRole(ROLE_ADMIN, ROLE_SELLER)
//
//                        .requestMatchers(
//                                "/cart/add_to_cart",
//                                "/cart/change_amount",
//                                "/cart/total_money",
//                                "/cart/c",
//                                "/cart/amount",
//                                "/cart/add_order"
//                        ).hasAnyRole(ROLE_USER)
//                        .anyRequest().authenticated()
//                )
//                .exceptionHandling(ex -> ex
//                        .authenticationEntryPoint((request, response, authException) ->
//                                response.sendRedirect("/account/login"))
//                        .accessDeniedHandler((request, response, accessDeniedException) -> {
//                            getError(accessDeniedException);
//                            getInfo(request);
//                        })
//                )
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//                        .maximumSessions(1) // 1 user can access with only 1 device, if he/she try to use with 2 or more, the previous session will immediately be canceled.
//                        .expiredUrl("/account/login")
//                )
//                .httpBasic(Customizer.withDefaults())
//                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
//                .build();
//    }
//
//    @Bean
//    public SecurityFilterChain accountFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .securityMatcher("/account/**")
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(
//                                "/account/login",
//                                "/account/add"
//                        ).permitAll()
//
//                        .requestMatchers(
//                                "/account/logout",
//                                "/account/refresh",
//                                "/account/edit_profile"
//                        ).hasAnyRole(
//                                ROLE_ADMIN,
//                                ROLE_SELLER,
//                                ROLE_USER
//                        )
//
//                        .requestMatchers(
//                                "/account/manage",
//                                "/account.load",
//                                "/account/delete"
//                        ).hasAnyRole(ROLE_ADMIN, ROLE_SELLER)
//                        .anyRequest().authenticated()
//                )
//                .exceptionHandling(ex -> ex
//                        .authenticationEntryPoint((request, response, authException) ->
//                                response.sendRedirect("/account/login"))
//                        .accessDeniedHandler((request, response, accessDeniedException) -> {
//                            getError(accessDeniedException);
//                            getInfo(request);
//                        })
//                )
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//                        .maximumSessions(1) // 1 user can access with only 1 device, if he/she try to use with 2 or more, the previous session will immediately be canceled.
//                        .expiredUrl("/account/login")
//                )
//                .httpBasic(Customizer.withDefaults())
//                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
//                .build();
//    }
//
//    @Bean
//    public SecurityFilterChain categoryFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .securityMatcher("/account/**")
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(
//                                "/category/"
//                        ).hasAnyRole(
//                                ROLE_ADMIN,
//                                ROLE_SELLER,
//                                ROLE_USER
//                        )
//                        .anyRequest().authenticated()
//                )
//                .exceptionHandling(ex -> ex
//                        .authenticationEntryPoint((request, response, authException) ->
//                                response.sendRedirect("/account/login"))
//                        .accessDeniedHandler((request, response, accessDeniedException) -> {
//                            getError(accessDeniedException);
//                            getInfo(request);
//                        })
//                )
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//                        .maximumSessions(1) // 1 user can access with only 1 device, if he/she try to use with 2 or more, the previous session will immediately be canceled.
//                        .expiredUrl("/account/login")
//                )
//                .httpBasic(Customizer.withDefaults())
//                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
//                .build();
//    }
//
//    @Bean
//    public SecurityFilterChain totalTargetSaleFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .securityMatcher("/sale/**")
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(
//                                "/sale/delete"
//                        ).hasAnyRole(
//                                ROLE_ADMIN,
//                                ROLE_SELLER
//                        )
//                        .anyRequest().authenticated()
//                )
//                .exceptionHandling(ex -> ex
//                        .authenticationEntryPoint((request, response, authException) ->
//                                response.sendRedirect("/account/login"))
//                        .accessDeniedHandler((request, response, accessDeniedException) -> {
//                            getError(accessDeniedException);
//                            getInfo(request);
//                        })
//                )
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//                        .maximumSessions(1) // 1 user can access with only 1 device, if he/she try to use with 2 or more, the previous session will immediately be canceled.
//                        .expiredUrl("/account/login")
//                )
//                .httpBasic(Customizer.withDefaults())
//                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
//                .build();
//    }
//
//    @Bean
//    public SecurityFilterChain reviewFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .securityMatcher("/review/**")
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(
//                                "/review/delete"
//                        ).hasAnyRole(
//                                ROLE_USER,
//                                ROLE_ADMIN,
//                                ROLE_SELLER
//                        )
//                        .anyRequest().authenticated()
//                )
//                .exceptionHandling(ex -> ex
//                        .authenticationEntryPoint((request, response, authException) ->
//                                response.sendRedirect("/account/login"))
//                        .accessDeniedHandler((request, response, accessDeniedException) -> {
//                            getError(accessDeniedException);
//                            getInfo(request);
//                        })
//                )
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//                        .maximumSessions(1) // 1 user can access with only 1 device, if he/she try to use with 2 or more, the previous session will immediately be canceled.
//                        .expiredUrl("/account/login")
//                )
//                .httpBasic(Customizer.withDefaults())
//                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
//                .build();
//    }
//
//    @Bean
//    public SecurityFilterChain quantitiesSoldFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .securityMatcher("/quantity/**")
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(
//                                "/quantity/delete"
//                        ).hasAnyRole(ROLE_SELLER)
//                        .anyRequest().authenticated()
//                )
//                .exceptionHandling(ex -> ex
//                        .authenticationEntryPoint((request, response, authException) ->
//                                response.sendRedirect("/account/login"))
//                        .accessDeniedHandler((request, response, accessDeniedException) -> {
//                            getError(accessDeniedException);
//                            getInfo(request);
//                        })
//                )
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//                        .maximumSessions(1) // 1 user can access with only 1 device, if he/she try to use with 2 or more, the previous session will immediately be canceled.
//                        .expiredUrl("/account/login")
//                )
//                .httpBasic(Customizer.withDefaults())
//                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
//                .build();
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http,
                                                       PasswordEncoder encoder,
                                                       UserDetailsService userDetailsService) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http
                .getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(encoder);

        return authenticationManagerBuilder.build();
    }

    private void getInfo(@NotNull HttpServletRequest request) {
        logger.info("request uri: {}", request.getRequestURI());
    }

    private void getError(@NotNull AccessDeniedException accessDeniedException) {
        logger.error("Error happens when exceptionHandling is working: {}", accessDeniedException.getMessage());
    }
}
