package com.huda.money_track.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationRequestDto {
    public String email;
    public String password;
}
