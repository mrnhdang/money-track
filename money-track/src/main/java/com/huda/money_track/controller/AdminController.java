package com.huda.money_track.controller;

import com.huda.money_track.dto.MemberInfoDto;
import com.huda.money_track.service.MemberService;
import com.huda.money_track.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
@AllArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {
    private TransactionService transactionService;
    private MemberService memberService;

    @GetMapping("/member")
    public ResponseEntity<List<MemberInfoDto>> listAllMember() {
        return new ResponseEntity<>(memberService.listAllMember(), HttpStatus.OK);
    }

    @DeleteMapping("/transaction/delete-all")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAllTransaction() {
        transactionService.deleteAllTransaction();
    }

    @DeleteMapping("/transaction/{id}//delete-all")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAllTransaction(@PathVariable("id") Integer memberId) {
        transactionService.deleteMemberAllTransaction(memberId);
    }

}
