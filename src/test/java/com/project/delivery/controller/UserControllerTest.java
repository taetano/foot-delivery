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
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@SpringBootTest(properties = {"spring.application.name=test", "service.jwt.access-expiration=3600"})
class UserControllerTest {
    static String AUTHORIZATON = "AUTHORIZATION";
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

    @Test
    void myProfile_simple() throws Exception{
        //  given
        LoginRequestDto loginRequestDto = new LoginRequestDto("username", "password");
        String token  = mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequestDto))
        ).andReturn().getResponse().getHeader(AUTHORIZATON);
        //  when


        mockMvc.perform(get("/api/users/1")
                .accept(MediaType.APPLICATION_JSON).header(AUTHORIZATON, token)
                )
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(UserController.class))
                .andExpect(handler().methodName("myProfile"))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.username", is("username")))
                .andExpect(jsonPath("$.nickname", is("nickname")))
                .andExpect(jsonPath("$.phone", is("01012341234")))
                .andExpect(jsonPath("$.address", is("address")))
                .andDo(print());
        //  then

    }
}