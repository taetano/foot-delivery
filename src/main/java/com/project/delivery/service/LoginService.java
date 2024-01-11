package com.project.delivery.service;

import com.project.delivery.dto.LoginRequestDto;
import com.project.delivery.entity.User;
import com.project.delivery.repository.UserRepository;
import com.project.delivery.util.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class LoginService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    // TODO: 2024/01/11 Set GlobalException AND Add Custom Exception
    public String login(LoginRequestDto dto) {
        User dbUser = userRepository.findByUsername(dto.username())
                .orElseThrow(RuntimeException::new);

        if (!passwordEncoder.matches(dto.password(), dbUser.getPassword())) {
            throw new RuntimeException("LoginService:21 -> 에러");
        }

        return jwtProvider.createToken(dto.username());
    }

}
