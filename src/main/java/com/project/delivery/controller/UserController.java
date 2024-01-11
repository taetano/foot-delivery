package com.project.delivery.controller;

import com.project.delivery.dto.SignupRequestDto;
import com.project.delivery.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RequestMapping("")
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping("/api/users/signup")
    public void signup(@RequestBody SignupRequestDto dto) {
        userService.signup(dto);
    }

}
