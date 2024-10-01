package com.example.UserService.service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    public String JWT_SECRET;

    public String generateToken(UserDetails userDetails, List<String> roles) {

        Algorithm algorithm = Algorithm.HMAC512(JWT_SECRET);
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withIssuedAt(LocalDateTime.now().toInstant(ZoneOffset.from(ZoneOffset.UTC)))
                .withExpiresAt(LocalDateTime.now().plusDays(2).toInstant(ZoneOffset.from(ZoneOffset.UTC)))
                .withIssuer("exemple@example.com")
                .withClaim("roles", roles)
                .sign(algorithm);

    }

}
