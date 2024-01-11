package com.project.delivery.service;

import com.project.delivery.dto.SignupRequestDto;
import com.project.delivery.entity.User;
import com.project.delivery.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(SignupRequestDto dto) {
        if (existsUsername(dto.username())) {
            throw new RuntimeException("Error occurred");
        }

        User newUser = User.builder()
                .username(dto.username())
                .password(passwordEncoder.encode(dto.password()))
                .nickname(dto.nickname())
                .phone(dto.phone())
                .address(dto.address())
                .build();

        userRepository.save(newUser);
    }

    public boolean existsUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
