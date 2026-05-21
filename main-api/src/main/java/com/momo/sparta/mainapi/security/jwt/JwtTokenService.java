package com.momo.sparta.mainapi.security.jwt;

import com.momo.sparta.commonmysqldb.entity.User;
import com.momo.sparta.commonmysqldb.repository.UserRepository;
import com.momo.sparta.mainapi.security.user.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtTokenService {

    private final SecretKey secretKey;
    private final JwtParser jwtParser;
    private final UserRepository userRepository;

    @Getter
    @Value("${jwt.expiration}")
    private int exp;
    @Value("${jwt.issuer}")
    private String issuer;
    @Value("${jwt.subject_seperator}")
    private String subjectSeperator;

    public JwtTokenService(
            @Value("${jwt.secret}") String secretKey,
            UserRepository userRepository
    ) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        this.jwtParser = Jwts.parser().verifyWith(this.secretKey).build();
        this.userRepository = userRepository;
    }

    private String issueToken(String subject) {
        return Jwts.builder()
                .issuer(issuer)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + exp * 1000L))
                .signWith(secretKey, Jwts.SIG.HS512)
                .subject(subject)
                .compact();
    }

    public String generateToken(CustomUserDetails customUserDetails) {
        return issueToken(customUserDetails.getEmail() + subjectSeperator + customUserDetails.getUsername());
    }

    public String refreshToken(String token) {
        Claims claims = jwtParser.parseSignedClaims(token).getPayload();
        if (claims.getExpiration().getTime() - new Date().getTime() > 5 * 60 * 1000L) {
            return token;
        }
        return issueToken(claims.getSubject());
    }

    public boolean validateToken(String token) {
        if (StringUtils.isBlank(token))
            return false;

        try {
            jwtParser.parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Optional<User> getUser(String token) {
        String[] jwtSubject = jwtParser
                .parseSignedClaims(token)
                .getPayload()
                .getSubject()
                .split(subjectSeperator);

        String email = jwtSubject[0];
        return userRepository.findByEmail(email);
    }

}
