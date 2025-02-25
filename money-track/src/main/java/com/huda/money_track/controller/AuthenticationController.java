package com.huda.money_track.controller;

import com.huda.money_track.config.jwt.JwtService;
import com.huda.money_track.config.jwt.UserInfoDetails;
import com.huda.money_track.dto.AuthenticationRequestDto;
import com.huda.money_track.dto.MemberInfoDto;
import com.huda.money_track.entity.Member;
import com.huda.money_track.exception.NotFoundException;
import com.huda.money_track.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {
    private MemberService memberService;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody Member userInfo) {
        return memberService.registerUser(userInfo);
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<MemberInfoDto> userProfile(@PathVariable("id") Integer memberId) {
        MemberInfoDto dto = memberService.getUserProfile(memberId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PatchMapping("/profile/edit")
    @ResponseStatus(HttpStatus.OK)
    public void updateProfile(@RequestBody MemberInfoDto dto) {
        memberService.updateProfile(dto);
    }

    @PostMapping("/login")
    public Map<String, Object> authenticateAndGetToken(@RequestBody AuthenticationRequestDto authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );
        if (authentication.isAuthenticated()) {
            UserInfoDetails userInfoDetails = (UserInfoDetails) authentication.getPrincipal();

            Map<String, Object> map = new HashMap<>();
            map.put("token", jwtService.generateToken(authRequest.getEmail()));
            map.put("authentication", userInfoDetails);

            return map;
        } else {
            throw new NotFoundException("Wrong email or password.");
        }
    }
}