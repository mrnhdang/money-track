package com.huda.money_track.controller;

import com.huda.money_track.dto.TransactionRecordDto;
import com.huda.money_track.entity.Transaction;
import com.huda.money_track.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@AllArgsConstructor
@RequestMapping("/api/transaction")
public class TransactionController {
    private TransactionService transactionService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<Transaction> getTransactionList(@RequestParam(name = "pageNumber", required = false, defaultValue = "0") int pageNumber) {
        return transactionService.getTransactionList(pageNumber);
    }

    @PostMapping("")
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionRecordDto dto) {
        Transaction transaction = transactionService.createNewTransaction(dto);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Integer updateTransaction(@RequestBody TransactionRecordDto dto, @PathVariable("id") Integer transactionId) {
        return transactionService.updateTransaction(dto, transactionId);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTransaction(@PathVariable("id") Integer transactionId) {
        transactionService.deleteTransactionByID(transactionId);
    }
}
