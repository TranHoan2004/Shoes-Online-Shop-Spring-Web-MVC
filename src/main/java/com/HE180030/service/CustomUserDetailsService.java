package com.HE180030.service;

import com.HE180030.model.Account;
import com.HE180030.repository.AccountRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomUserDetailsService implements UserDetailsService {
    Logger log = Logger.getLogger(CustomUserDetailsService.class.getName());
    AccountRepository repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("loadUserByUsername");
        Account acc = repo.findAccountByEmail(email);
        if (acc == null) {
            throw new UsernameNotFoundException(email);
        }
        GrantedAuthority auth = new SimpleGrantedAuthority(acc.getRole().toString());
        return new User(acc.getEmail(), acc.getPassword(), List.of(auth));
    }
}
