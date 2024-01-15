package com.project.delivery.dto;

import com.project.delivery.entity.StoreCategory;
import com.project.delivery.enums.StoreStatus;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
public final class StoreDto {
    private final Long id;
    private final String ownerName;
    private final String title;
    private final LocalDateTime openTime;
    private final LocalDateTime closeTime;
    private final String address;
    private final String phone;
    private final StoreStatus status;
    private final StoreCategory category;

    public StoreDto(Long id, String ownerName, String title, LocalDateTime openTime, LocalDateTime closeTime, String address, String phone, StoreStatus status, StoreCategory category) {
        this.id = id;
        this.ownerName = ownerName;
        this.title = title;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.address = address;
        this.phone = phone;
        this.status = status;
        this.category = category;
    }
}
