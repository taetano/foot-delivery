package com.project.delivery.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false, unique = true, length = 11)
    private String phone;

    @Column(nullable = false)
    private String address;

    // coupon, cart, like, order, payment

    @Builder
    public User(String username, String password, String nickname, String phone, String address) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.phone = phone;
        this.address = address;
    }
}
