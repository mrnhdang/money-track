package com.huda.money_track.service;

import com.huda.money_track.config.jwt.UserInfoDetails;
import com.huda.money_track.dto.MemberInfoDto;
import com.huda.money_track.entity.Member;
import com.huda.money_track.entity.Role;
import com.huda.money_track.exception.InvalidInputParameter;
import com.huda.money_track.exception.NotFoundException;
import com.huda.money_track.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
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
        if (memberRepository.findByEmail(userInfo.getEmail()).isPresent()) {
            throw new InvalidInputParameter("Email has already exist.");
        }
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

    public Member checkExistMember(Integer memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new NotFoundException("Member does not exist."));
    }

    public MemberInfoDto getUserProfile(Integer memberId) {
        Member member = checkExistMember(memberId);
        return MemberInfoDto.builder()
                .memberId(memberId)
                .qr(member.getQr())
                .username(member.getUsername())
                .image(member.getEmail())
                .email(member.getEmail())
                .balance(member.getBalance())
                .build();
    }

    public void updateProfile(MemberInfoDto dto) {
        Member member = checkExistMember(dto.getMemberId());
        if (memberRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new InvalidInputParameter("Email has already exist.");
        }
        Optional.ofNullable(dto.getBalance()).ifPresent(member::setBalance);
        Optional.ofNullable(dto.getImage()).ifPresent(member::setImage);
        Optional.ofNullable(dto.getUsername()).ifPresent(member::setUsername);
        Optional.ofNullable(dto.getQr()).ifPresent(member::setQr);
        Optional.ofNullable(dto.getEmail()).ifPresent(member::setEmail);

        memberRepository.save(member);
    }
}
