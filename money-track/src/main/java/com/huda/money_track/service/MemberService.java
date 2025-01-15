package com.huda.money_track.service;

import com.huda.money_track.config.jwt.UserInfoDetails;
import com.huda.money_track.dto.MemberInfoDto;
import com.huda.money_track.entity.Member;
import com.huda.money_track.entity.Role;
import com.huda.money_track.exception.NotFoundException;
import com.huda.money_track.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService implements UserDetailsService {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> member = memberRepository.findByEmail(username);
        return member.map(UserInfoDetails::new).orElseThrow(() -> new NotFoundException("Member not exist."));
    }

    public String registerUser(Member userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        memberRepository.save(userInfo);
        return "Register User successfully";
    }

    public List<MemberInfoDto> listAllMember() {
        List<Member> members = memberRepository.findAllByRole(Role.MEMBER);
        return members.stream().map(member -> MemberInfoDto.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .username(member.getUsername())
                .qr(member.getQr())
                .image(member.getImage())
                .balance(member.getBalance())
                .build()).toList();
    }
}
