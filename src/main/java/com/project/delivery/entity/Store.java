package com.project.delivery.entity;

import com.project.delivery.enums.StoreStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String ownerName;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDateTime openTime;

    @Column(nullable = false)
    private LocalDateTime closeTime;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phone;

    @Enumerated(EnumType.STRING)
    private StoreStatus status;

    @ManyToOne
    private StoreCategory category;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    @Builder
    public Store(String ownerName, String title, LocalDateTime openTime, LocalDateTime closeTime, String address, String phone, StoreStatus status, StoreCategory category) {
        this.ownerName = ownerName;
        this.title = title;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.address = address;
        this.phone = phone;
        this.status = StoreStatus.CLOSE;
        this.category = category;
        this.createdAt = LocalDateTime.now();
    }
}
