package com.project.delivery.enums;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class StoreStatus {
    private String name;

    public StoreStatus(String name) {
        this.name = name;
    }
}
