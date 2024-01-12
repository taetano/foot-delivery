package com.project.delivery.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.delivery.dto.LoginRequestDto;
import com.project.delivery.dto.SignupRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@AutoConfigureMockMvc
@SpringBootTest(properties = {"spring.application.name=test", "service.jwt.access-expiration=60", "service.jwt.secret-key=secret"})
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void signup_simple() throws Exception {
        //  given
        SignupRequestDto signupRequestDto = new SignupRequestDto(
                "username",
                "password",
                "nickname",
                "01012341234",
                "address"
        );
        //  when
        mockMvc.perform(post("/api/users/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signupRequestDto))
        ).andDo(print());
        //  then
    }

    @Test
    void login_simple() throws Exception{
        //  given
        LoginRequestDto loginRequestDto = new LoginRequestDto("username", "password");
        //  when
        MvcResult mvcResult = mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequestDto))
                ).andDo(print())
                .andReturn();
        //  then
        assertThat(mvcResult.getResponse().getHeader("AUTHORIZATION")).isNotEmpty();
    }
}