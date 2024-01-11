package com.project.delivery.dto;

public record SignupRequestDto(
        String username, String password, String nickname, String phone, String address) {
}
