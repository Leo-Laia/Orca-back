package com.orca.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthRequest {

    @NotNull(message = "O username é obrigatório")
    private String username;

    @NotNull(message = "A senha é obrigatória")
    private String password;
}