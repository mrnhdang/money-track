package com.huda.money_track.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfoDto {
    private Integer memberId;
    private String username;
    private String email;
    private BigDecimal balance;
    private String image;
    private String qr;
}
