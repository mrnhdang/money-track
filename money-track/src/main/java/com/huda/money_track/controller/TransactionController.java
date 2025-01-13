package com.huda.money_track.controller;

import com.huda.money_track.dto.TransactionRecordDto;
import com.huda.money_track.entity.Transaction;
import com.huda.money_track.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
@AllArgsConstructor
@RequestMapping("/api/transaction")
public class TransactionController {
    private TransactionService transactionService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<Transaction> getMemberTransactionList(@RequestParam(name = "pageNumber", required = false, defaultValue = "0") int pageNumber,
                                                      @RequestParam(name = "pageSize", required = false, defaultValue = "5") int pageSize,
                                                      @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy,
                                                      @RequestParam(name = "memberId", required = false, defaultValue = "") Integer memberId) {
        return transactionService.getMemberTransactionList(pageNumber, pageSize, sortBy, memberId);
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('MEMBER')")
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionRecordDto dto) {
        Transaction transaction = transactionService.createNewTransaction(dto);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    @PatchMapping("{id}")
    @PreAuthorize("hasAuthority('MEMBER')")
    @ResponseStatus(HttpStatus.OK)
    public Integer updateTransaction(@RequestBody TransactionRecordDto dto, @PathVariable("id") Integer transactionId) {
        return transactionService.updateTransaction(dto, transactionId);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('MEMBER')")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTransaction(@PathVariable("id") Integer transactionId) {
        transactionService.deleteTransactionByID(transactionId);
    }
}
