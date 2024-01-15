package com.project.delivery.service;

import com.project.delivery.dto.MyProfileResponseDto;
import com.project.delivery.dto.SignupRequestDto;
import com.project.delivery.entity.User;
import com.project.delivery.exception.ProfileNotFoundExistException;
import com.project.delivery.exception.UsernameAlreadyExistException;
import com.project.delivery.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @InjectMocks
    UserService userService;

    @DisplayName("성공 케이스")
    @Nested
    class Success {
        @Captor
        ArgumentCaptor<User> userCaptor;

        @DisplayName("signup(): 올바른 입력값으로 사용자 등록")
        @Test
        void signup() {
            //  given
            SignupRequestDto dto = mock(SignupRequestDto.class);
            given(dto.username()).willReturn("username");
            given(userRepository.existsByUsername(anyString())).willReturn(false);
            given(dto.password()).willReturn("password");
            given(passwordEncoder.encode(any(CharSequence.class))).willReturn("encodedPassword");

            //  when
            userService.signup(dto);

            //  then
            verify(userRepository).existsByUsername(anyString());
            verify(userRepository).save(userCaptor.capture());
            assertThat(dto.password()).isNotEqualTo(userCaptor.getValue().getPassword());
        }

        @DisplayName("existsUsername(): 유저가 존재할 시 true 반환")
        @Test
        void existsUsername_true() {
            //  given
            given(userRepository.existsByUsername(anyString())).willReturn(true);
            //  when
            boolean result = userService.existsUsername("username");
            //  then
            assertThat(result).isTrue();
            verify(userRepository).existsByUsername(anyString());

        }

        @DisplayName("existsUsername(): 유저가 존재하지 않을 시 false 반환")
        @Test
        void existsUsername_fail() {
            //  given
            given(userRepository.existsByUsername(anyString())).willReturn(false);
            //  when
            boolean result = userService.existsUsername("username");
            //  then
            assertThat(result).isFalse();
            verify(userRepository).existsByUsername(anyString());

        }

        @DisplayName("myProfile(): {id}값과 일치하는 유저 정보를 반환")
        @Test
        void myProfile() {
            //  given
            User mockUser = mock(User.class);
            given(mockUser.getId()).willReturn(1L);
            given(mockUser.getUsername()).willReturn("username");
            given(mockUser.getNickname()).willReturn("nickname");
            given(mockUser.getPhone()).willReturn("01012341234");
            given(mockUser.getAddress()).willReturn("address");
            given(userRepository.findById(anyLong())).willReturn(Optional.of(mockUser));
            //  when
            MyProfileResponseDto result = userService.myProfile(1L);
            //  then
            assertThat(result).usingRecursiveComparison().isEqualTo(mockUser);
        }
    }

    @DisplayName("실패 케이스")
    @Nested
    class Fail {
        @DisplayName("중복된 Username 으로 회원가입시 UsernameAlreadyExistException 반환")
        @Test
        void signup_throw_UsernameAlreadyExistException() {
            //  given
            SignupRequestDto dto = mock(SignupRequestDto.class);
            given(dto.username()).willReturn("username");
            given(userRepository.existsByUsername(anyString())).willReturn(true);
            given(userService.existsUsername(anyString())).willReturn(true);

            //  when
            assertThatThrownBy(() -> userService.signup(dto))
                    .isInstanceOf(UsernameAlreadyExistException.class);

            //  then
            verify(userRepository).existsByUsername(anyString());
        }

        @DisplayName("{id}값과 일치하는 유저 정보가 없을 시 ProfileNotFoundException 반환")
        @Test
        void myProfile_throw_ProfileNotFoundException() {
            //  given
            given(userRepository.findById(anyLong())).willReturn(Optional.empty());
            //  when
            assertThatThrownBy(() -> userService.myProfile(1L))
                    .isInstanceOf(ProfileNotFoundExistException.class);
            //  then
            verify(userRepository).findById(anyLong());
        }
    }
}