package com.project.delivery.exception;

import com.project.delivery.dto.ApiError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> runtimeException(RuntimeException exception) {
        return ResponseEntity
                .status(400)
                .body(new ApiError(
                        400,
                        exception.getMessage(),
                        LocalDateTime.now())
                );
    }
}
