package com.huda.money_track.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    @Column(name = "email", unique = true)
    private String email;
    private String image;
    private String qr;
    private String password;
    private BigDecimal balance;
    @Enumerated(EnumType.STRING)
    private Role role;
}
