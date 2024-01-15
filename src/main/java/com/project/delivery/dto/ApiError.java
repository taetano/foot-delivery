package com.project.delivery.dto;

import java.time.LocalDateTime;

public record ApiError(int code, String msg, LocalDateTime createdAt) {
}
