package com.italomlaino.swspeciesmatcher.api.controller;

import com.italomlaino.swspeciesmatcher.api.dto.LoginResultDto;
import com.italomlaino.swspeciesmatcher.api.exception.InvalidCredentialsException;
import com.italomlaino.swspeciesmatcher.api.service.CredentialsService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class AuthenticationController {

    private static final String BEARER = "Bearer ";
    private static final String AUTHORITIES = "authorities";
    private static final String ROLE_USER = "ROLE_USER";

    @Value("${token.secret.key}")
    private String secretKey;
    @Value("${token.expiration}")
    private int expiration;

    private CredentialsService credentialsService;

    @Autowired
    public AuthenticationController(CredentialsService credentialsService) {
        this.credentialsService = credentialsService;
    }

    @PostMapping("/authentication")
    public LoginResultDto login(@RequestParam("username") String username, @RequestParam("password") String password) {
        boolean validCredentials = credentialsService.isValid(username, password);
        if (!validCredentials)
            throw new InvalidCredentialsException();

        String token = buildToken(username);

        LoginResultDto user = new LoginResultDto();
        user.setUsername(username);
        user.setToken(token);

        return user;
    }

    private String buildToken(String username) {
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList(ROLE_USER);

        String token = Jwts
                .builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(username)
                .claim(AUTHORITIES,
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey)), SignatureAlgorithm.HS512)
                .compact();

        return BEARER + token;
    }
}
