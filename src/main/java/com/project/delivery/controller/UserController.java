package com.project.delivery.controller;

import com.project.delivery.dto.LoginRequestDto;
import com.project.delivery.dto.MyProfileResponseDto;
import com.project.delivery.dto.SignupRequestDto;
import com.project.delivery.service.LoginService;
import com.project.delivery.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RequestMapping("/api/users")
@RestController
public class UserController {
    private final UserService userService;
    private final LoginService loginService;

    @PostMapping("/signup")
    public void signup(@RequestBody SignupRequestDto dto) {
        userService.signup(dto);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequestDto dto) {
        String token = loginService.login(dto);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        return ResponseEntity.ok().headers(headers).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MyProfileResponseDto> myProfile(@PathVariable("id") Long id) {
        MyProfileResponseDto myProfile = userService.myProfile(id);
        return ResponseEntity.ok(myProfile);
    }
}
