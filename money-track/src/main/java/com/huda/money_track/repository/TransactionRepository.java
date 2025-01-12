package com.huda.money_track.repository;

import com.huda.money_track.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    

}
