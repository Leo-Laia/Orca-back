package com.orca.security;

import com.orca.model.User;
import lombok.Getter;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public class CustomUserDetails implements UserDetails {

    private final UUID id;
    private final String username;
    private final String password;
    private final boolean active;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(User user) {
        this.id = user.getId();
        this.username = user.getLogin();
        this.password = user.getPassword();
        this.active = user.getActive();
        this.authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isAccountNonExpired() {
        return active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return active;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}