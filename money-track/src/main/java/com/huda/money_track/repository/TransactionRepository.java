package com.huda.money_track.repository;

import com.huda.money_track.entity.Member;
import com.huda.money_track.entity.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findAllByMember(Member member, Pageable pageable);
    void deleteAllByMember(Member member);
    Integer countByMember(Member member);
}
