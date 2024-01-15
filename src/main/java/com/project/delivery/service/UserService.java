package com.project.delivery.service;

import com.project.delivery.dto.MyProfileResponseDto;
import com.project.delivery.dto.SignupRequestDto;
import com.project.delivery.entity.User;
import com.project.delivery.exception.ProfileNotFoundExistException;
import com.project.delivery.exception.UsernameAlreadyExistException;
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

    @Transactional
    // TODO: 2024/01/12 DTO의 값이 서비스에 부합한 정보인지 확인하는 Validation 추가 필요
    public void signup(SignupRequestDto dto) {
        if (existsUsername(dto.username())) {
            throw new UsernameAlreadyExistException(String.format("해당 Username:{%s} 이 존재합니다.", dto.username()));
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

    public MyProfileResponseDto myProfile(Long id) {
        User dbUser = userRepository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundExistException(String.format("ID:{%d} 와 일치하는 프로필을 찾을 수 없습니다.", id) ));

        return new MyProfileResponseDto(
                dbUser.getId(),
                dbUser.getUsername(),
                dbUser.getNickname(),
                dbUser.getPhone(),
                dbUser.getAddress()
                );
    }
}
