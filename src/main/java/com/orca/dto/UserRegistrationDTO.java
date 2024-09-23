package com.orca.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRegistrationDTO {
    private String name;
    private String username;
    private String password;
}