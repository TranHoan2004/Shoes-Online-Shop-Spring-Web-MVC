package com.HE180030.security;

import com.HE180030.dto.AccountDTO;
import com.HE180030.service.AccountService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final AccountService service;

    public SecurityConfig(AccountService service) {
        this.service = service;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.
                csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/home", "/").permitAll()
                ).formLogin(login -> login
                        .loginPage("/login").permitAll()
                ).rememberMe(me -> me
                        .key("uniqueAndSecret")
                ).httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();
        List<AccountDTO> accountDTOs = service.getAllAccountDTOs();
        accountDTOs.forEach(acc -> {
            User.UserBuilder builder = User.builder()
                    .username(acc.getUsername())
                    .password(acc.getPassword());
            if (acc.getIsAdmin()) {
                builder.roles("ADMIN");
            } else if (acc.getIsSell()) {
                builder.roles("SELLER");
            } else {
                builder.roles("USER");
            }
            UserDetails user = builder.build();
            userDetailsService.createUser(user);
        });
        return userDetailsService;
    }
}
