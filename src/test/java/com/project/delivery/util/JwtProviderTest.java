package com.project.delivery.util;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class JwtProviderTest {
    JwtProvider jwtProvider;
    String token;

    @BeforeEach
    public void setup() {
        jwtProvider = new JwtProvider();
        ReflectionTestUtils.setField(jwtProvider, "issuer", "test");
        ReflectionTestUtils.setField(jwtProvider, "accessExpiration", 36000L);
        token = jwtProvider.createToken("username");
    }

    @Test
    void createToken() {
        //  given

        //  when
        String token = jwtProvider.createToken("username");
        //  then
        assertThat(token).isNotEmpty();
    }

    @Test
    void resolveToken() {
        //  given
        String readyToken = token;
        HttpServletRequest request = mock(HttpServletRequest.class);
        given(request.getHeader(anyString())).willReturn("Bearer " + readyToken);

        //  when
        String token = jwtProvider.resolveToken(request).get();
        //  then
        assertThat(token).isNotEmpty();
        assertThat(token).isEqualTo(readyToken);
    }

    @Test
    void getUsernameFromToken() {
        //  given

        //  when
        String username = jwtProvider.getUsernameFromToken(token);
        //  then
        assertThat(username).isEqualTo("username");
    }
}