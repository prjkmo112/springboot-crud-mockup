package com.momo.sparta.mainapi.security;

import com.momo.sparta.commonmysqldb.entity.User;
import com.momo.sparta.mainapi.security.jwt.JwtTokenService;
import com.momo.sparta.mainapi.security.user.CustomUserDetails;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

@Service
@RequiredArgsConstructor
public class AuthTokenService {

    private final JwtTokenService jwtTokenService;

    public String extractToken(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, "X-Auth-Token");
        String cookieToken = cookie != null ? cookie.getValue() : null;
        return cookieToken != null ? cookieToken : request.getHeader("X-Auth-Token");
    }

    public String generateToken(Authentication authentication) {
        if (authentication == null)
            return null;

        CustomUserDetails userDetails;
        if (authentication.getPrincipal() instanceof CustomUserDetails)
            userDetails = (CustomUserDetails) authentication.getPrincipal();
        else
            return null;

        return jwtTokenService.generateToken(userDetails);
    }

    public void setTokenToCookie(HttpServletResponse response, String token, int maxAge) {
        Cookie cookie = new Cookie("X-Auth-Token", token);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setAttribute("SameSite", "None");

        response.addCookie(cookie);
    }

    public String refreshToken(String token) {
        return jwtTokenService.refreshToken(token);
    }

    public int getTokenExp() {
        return jwtTokenService.getExp();
    }

    public User getUser(String token) {
        return jwtTokenService.getUser(token).orElse(null);
    }

    public boolean isValidate(String token) {
        return jwtTokenService.validateToken(token);
    }

}
