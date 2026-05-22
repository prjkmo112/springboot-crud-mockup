package com.momo.sparta.mainapi.security;

import com.momo.sparta.commonmysqldb.entity.User;
import com.momo.sparta.mainapi.security.user.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthenticationProcessingFilter extends OncePerRequestFilter {

    private final AuthTokenService authTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = authTokenService.extractToken(request);

        if (token != null && authTokenService.isValidate(token)) {
            User user = authTokenService.getUser(token);
            if (user != null) {
                CustomUserDetails userDetails = new CustomUserDetails(user);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                String newToken = authTokenService.refreshToken(token);
                if (!newToken.equals(token))
                    authTokenService.setTokenToCookie(response, newToken, authTokenService.getTokenExp());
            }
        }

        filterChain.doFilter(request, response);
    }
}
