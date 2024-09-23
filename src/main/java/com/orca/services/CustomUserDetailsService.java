package com.orca.services;

import com.orca.model.User;
import com.orca.repositories.UserRepository;
import com.orca.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with login: " + username));

        return new CustomUserDetails(user);
    }
}