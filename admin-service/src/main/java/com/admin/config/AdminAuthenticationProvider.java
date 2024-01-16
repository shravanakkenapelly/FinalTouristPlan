package com.admin.config;

import java.util.Base64;
import java.util.Collections;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.admin.dto.AdminDto;
import com.admin.serviceimpl.AdminServiceImpl;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class AdminAuthenticationProvider {

    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

    private final AdminServiceImpl adminService;

    @PostConstruct
    protected void init() {
        // this is to avoid having the raw secret key available in the JVM
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(AdminDto admin) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + 3600000); // 1 hour

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create()
                .withSubject(admin.getEmail())
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .withClaim("firstName", admin.getFullName())
                .sign(algorithm);
    }

    public Authentication validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        JWTVerifier verifier = JWT.require(algorithm)
                .build();

        DecodedJWT decoded = verifier.verify(token);

        AdminDto admin = AdminDto.builder()
                .email(decoded.getSubject())
                .fullName(decoded.getClaim("firstName").asString())
                .build();

        return new UsernamePasswordAuthenticationToken(admin, null, Collections.emptyList());
    }

    public Authentication validateTokenStrongly(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        JWTVerifier verifier = JWT.require(algorithm)
                .build();

        DecodedJWT decoded = verifier.verify(token);

        AdminDto admin = adminService.findByLoginEmail(decoded.getSubject());

        return new UsernamePasswordAuthenticationToken(admin, null, Collections.emptyList());
    }

}
