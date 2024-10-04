package com.orca.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orca.dto.CreateUserRequest;
import com.orca.dto.CreateUserResponse;
import com.orca.model.User;
import com.orca.repositories.RoleRepository;
import com.orca.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ObjectMapper mapper;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public CreateUserResponse createUser(CreateUserRequest dto) {
        User user = mapper.convertValue(dto, User.class);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        roleRepository.findByName("ROLE_SIMPLE").ifPresent(role -> user.getRoles().add(role));


        return mapper.convertValue(
                userRepository.save(user),
                CreateUserResponse.class);
    }
}
