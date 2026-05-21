package com.momo.sparta.mainapi.domains.user.service;

import com.momo.sparta.commonmysqldb.entity.User;
import com.momo.sparta.commonmysqldb.repository.UserRepository;
import com.momo.sparta.mainapi.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElse(null);
    }

    public void registerUser(User user) {
        User checkUser = getUserByEmail(user.getEmail());

        if (checkUser != null)
            throw new ApiException("User already exists");

        userRepository.save(user);
    }

}
