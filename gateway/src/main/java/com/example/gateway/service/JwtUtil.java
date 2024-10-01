package com.example.gateway.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import com.auth0.jwt.algorithms.Algorithm;

@Service
@RefreshScope
public class JwtUtil {

    @Value("${jwt.secret}")
    private String JWT_SERVICE;

    public String decodeToken(String accessToken){
        Algorithm algorithm = Algorithm.HMAC512(JWT_SERVICE);
        accessToken = accessToken.replace("Bearer", "").strip();
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("exemple@example.com")
                .build();
        DecodedJWT verified = verifier.verify(accessToken);
        return verified.getSubject();
    }

}
