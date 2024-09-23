package com.orca.resources;

import com.orca.dto.AuthRequest;
import com.orca.dto.AuthResponse;
import com.orca.security.JwtTokenProvider;
import com.orca.services.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationResource {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    request.getUsername(), request.getPassword());

            authenticationManager.authenticate(authToken);

            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
            String token = jwtTokenProvider.createToken(
                    userDetails.getUsername(),
                    userDetails.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .toList()
            );

            return ResponseEntity.ok(new AuthResponse(token));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username/password supplied");
        }
    }
}
