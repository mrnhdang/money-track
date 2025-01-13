package com.huda.money_track.config.jwt;

import com.huda.money_track.entity.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserInfoDetails implements UserDetails {
    @Getter
    private final Integer id;
    @Getter
    private final BigDecimal balance;
    private final String username;
    private final String password;
    private final List<GrantedAuthority> grantedAuthority;

    public UserInfoDetails(Member userInfo) {
        this.username = userInfo.getEmail(); // Assuming 'name' is used as 'username'
        this.password = userInfo.getPassword();
        this.grantedAuthority = Stream.of(userInfo.getRole().toString())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        this.balance = userInfo.getBalance();
        this.id = userInfo.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthority;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
