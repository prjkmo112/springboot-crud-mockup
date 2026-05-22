package com.momo.sparta.mainapi.domains.user.controller;

import com.momo.sparta.commonmysqldb.entity.User;
import com.momo.sparta.commonmysqldb.entity.UserRoleEnum;
import com.momo.sparta.mainapi.domains.user.dto.CreateUserDto;
import com.momo.sparta.mainapi.domains.user.dto.LoginUserDto;
import com.momo.sparta.mainapi.domains.user.dto.UserDto;
import com.momo.sparta.mainapi.domains.user.mapper.UserMapper;
import com.momo.sparta.mainapi.domains.user.service.AuthService;
import com.momo.sparta.mainapi.security.AuthTokenService;
import com.momo.sparta.mainapi.security.user.CustomUserDetails;
import com.momo.sparta.mainapi.security.user.UserInfo;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final AuthTokenService authTokenService;

    @PostMapping("/register")
    public UserDto registerUser(@RequestBody @Valid CreateUserDto createUserDto) {
        User user = User.builder()
                .name(createUserDto.getName())
                .email(createUserDto.getEmail())
                .passwd(passwordEncoder.encode(createUserDto.getPasswd()))
                .role(UserRoleEnum.USER)
                .build();

        authService.registerUser(user);
        return UserMapper.INSTANCE.toUserDto(user);
    }

    @PostMapping("/login")
    public UserDto loginUser(@RequestBody @Valid LoginUserDto loginUserDto, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUserDto.getEmail(), loginUserDto.getPasswd())
        );

        String token = authTokenService.generateToken(authentication);
        authTokenService.setTokenToCookie(response, token, authTokenService.getTokenExp());

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        return UserDto.builder()
                .name(userDetails.getUsername())
                .email(userDetails.getEmail())
                .isLoginned(true)
                .build();
    }

    @PostMapping("/check")
    public UserDto checkUser(@UserInfo CustomUserDetails customUserDetails) {
        if (customUserDetails == null) {
            return UserDto.builder()
                    .isLoginned(false)
                    .build();
        }

        UserDto userDto = UserMapper.INSTANCE.toUserDto(customUserDetails);
        userDto.setIsLoginned(true);
        return userDto;
    }

}
