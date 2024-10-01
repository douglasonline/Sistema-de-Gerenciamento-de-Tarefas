package com.example.UserService.service;

import com.example.UserService.model.Role;
import com.example.UserService.model.User;
import com.example.UserService.payload.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public User register(User user) {
        User user1 = User.builder()
                .name(user.getName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .role(Role.USER)
                .username(user.getUsername())
                .build();
        return userService.save(user1);
    }

    public String authenticate(LoginRequest loginRequest) {
        User user = userService.findByEmail(loginRequest.email())
                .orElseThrow(() -> new UsernameNotFoundException("Autenticação falhou"));

        if (!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            throw new BadCredentialsException("Autenticação falhou");
        }

        UsernamePasswordAuthenticationToken userAuth = new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password());
        Authentication authenticate = authenticationManager.authenticate(userAuth);
        User usuarioAutenticado = (User) authenticate.getPrincipal();
        return jwtService.generateToken(usuarioAutenticado, getRoles(authenticate.getAuthorities()));
    }

    public List<String> getRoles(Collection<? extends GrantedAuthority> authorities) {

        return authorities.stream().map(GrantedAuthority::getAuthority).toList();

    }

}
