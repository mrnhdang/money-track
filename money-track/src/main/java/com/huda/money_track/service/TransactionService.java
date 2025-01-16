package com.huda.money_track.service;

import com.huda.money_track.dto.TransactionRecordDto;
import com.huda.money_track.entity.Member;
import com.huda.money_track.entity.Transaction;
import com.huda.money_track.exception.NotFoundException;
import com.huda.money_track.repository.MemberRepository;
import com.huda.money_track.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class TransactionService {
    private TransactionRepository transactionRepository;
    private MemberRepository memberRepository;

    public List<Transaction> getMemberTransactionList(int pageNumber, int pageSize, String sortBy, Integer memberId) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
        if (memberId != null) {
            Member member = checkExistMember(memberId);
            return transactionRepository.findAllByMember(member, pageable);
        }
        return transactionRepository.findAll(pageable).getContent();
    }

    private Member checkExistMember(Integer memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new NotFoundException("Member does not exist."));
    }

    public Transaction createNewTransaction(TransactionRecordDto dto) {
        Member member = checkExistMember(dto.memberId());
        Transaction mappedTransaction = Transaction.builder()
                .transactionDatetime(dto.transactionDatetime())
                .amount(dto.amount())
                .description(dto.description())
                .member(member)
                .build();

        // update member's balance
        updateMemberBalance(member, dto.amount());
        return transactionRepository.save(mappedTransaction);
    }

    public Integer updateTransaction(TransactionRecordDto dto, Integer transactionId) {
        Member member = checkExistMember(dto.memberId());
        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(() -> new NotFoundException("Transaction does not exist."));
        Optional.of(dto.transactionDatetime()).ifPresent(transaction::setTransactionDatetime);
        Optional.of(dto.description()).ifPresent(transaction::setDescription);
        Optional.of(member).ifPresent(transaction::setMember);
        Optional.of(dto.amount()).ifPresent(transaction::setAmount);
        Transaction createdTransaction = transactionRepository.save(transaction);

        // update member's balance
        updateMemberBalance(member, dto.amount());
        return createdTransaction.getId();
    }

    private void updateMemberBalance(Member member, BigDecimal amount) {
        BigDecimal newBalance = member.getBalance().add(amount);
        member.setBalance(newBalance);
        memberRepository.save(member);
    }

    public void deleteTransactionByID(Integer transactionId) {
        transactionRepository.deleteById(transactionId);
    }

    public void deleteAllTransaction() {
        transactionRepository.deleteAll();
    }

    public void deleteMemberAllTransaction(Integer memberId) {
        Member member = checkExistMember(memberId);
        transactionRepository.deleteAllByMember(member);
    }
}
