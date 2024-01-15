package com.project.delivery.service;

import com.project.delivery.dto.LoginRequestDto;
import com.project.delivery.entity.User;
import com.project.delivery.exception.LoginException;
import com.project.delivery.repository.UserRepository;
import com.project.delivery.util.JwtProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    JwtProvider jwtProvider;
    @InjectMocks
    LoginService loginService;

    @Test
    void login_success() {
        //  given
        LoginRequestDto mockedDto = mock(LoginRequestDto.class);
        User mockedUser = mock(User.class);
        given(mockedDto.username()).willReturn("username");
        given(mockedDto.password()).willReturn("password");
        given(userRepository.findByUsername(anyString())).willReturn(Optional.of(mockedUser));
        given(mockedUser.getPassword()).willReturn("password");
        given(passwordEncoder.matches(any(CharSequence.class), anyString())).willReturn(true);
        given(jwtProvider.createToken(anyString())).willReturn("createdToken");
        //  when
        String token = loginService.login(mockedDto);
        //  then
        verify(userRepository).findByUsername(anyString());
        verify(passwordEncoder).matches(any(CharSequence.class), anyString());
        verify(jwtProvider).createToken(anyString());
        assertThat(token).isEqualTo("createdToken");
    }

    @Test
    void login_fail_byWrongUsername() {
        //  given
        LoginRequestDto mockedDto = mock(LoginRequestDto.class);
        given(mockedDto.username()).willReturn("username");
        given(userRepository.findByUsername(anyString())).willReturn(Optional.empty());
        //  when
        assertThatThrownBy(() -> loginService.login(mockedDto))
                .isInstanceOf(LoginException.class);
        //  then
        verify(userRepository).findByUsername(anyString());
    }

    @Test
    void login_fail_byWrongPassword() {
        //  given
        LoginRequestDto mockedDto = mock(LoginRequestDto.class);
        User mockedUser = mock(User.class);
        given(mockedDto.username()).willReturn("username");
        given(mockedDto.password()).willReturn("WrongPassword");
        given(userRepository.findByUsername(anyString())).willReturn(Optional.of(mockedUser));
        given(mockedUser.getPassword()).willReturn("password");
        given(passwordEncoder.matches(any(CharSequence.class), anyString())).willReturn(false);
        //  when
        assertThatThrownBy(() -> loginService.login(mockedDto))
                .isInstanceOf(LoginException.class);
        //  then
        verify(userRepository).findByUsername(anyString());
        verify(passwordEncoder).matches(any(CharSequence.class), anyString());
    }
}