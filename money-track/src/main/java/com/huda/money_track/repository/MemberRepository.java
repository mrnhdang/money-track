package com.huda.money_track.repository;

import com.huda.money_track.entity.Member;
import com.huda.money_track.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface MemberRepository extends JpaRepository<Member, Integer> {
    Optional<Member> findByEmail(String email);
    
    List<Member> findAllByRole(Role role); 
}
