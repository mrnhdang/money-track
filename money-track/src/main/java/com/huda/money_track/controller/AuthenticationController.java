package com.huda.money_track.controller;

import com.huda.money_track.config.jwt.JwtService;
import com.huda.money_track.dto.AuthenticationRequestDto;
import com.huda.money_track.dto.MemberInfoDto;
import com.huda.money_track.entity.Member;
import com.huda.money_track.exception.NotFoundException;
import com.huda.money_track.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
    public void updateProfile(@RequestBody MemberInfoDto dto){
        memberService.updateProfile(dto);
    }

    @PostMapping("/login")
    public String authenticateAndGetToken(@RequestBody AuthenticationRequestDto authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getEmail());
        } else {
            throw new NotFoundException("Invalid user request!");
        }
    }
}