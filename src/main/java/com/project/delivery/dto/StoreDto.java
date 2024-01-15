package com.project.delivery.dto;


import java.time.LocalDateTime;
public record StoreDto(Long id, String ownerName, String title, LocalDateTime openTime, LocalDateTime closeTime,
                       String address, String phone, String status, String category) {
}
