package com.huda.money_track.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionRecordDto(String description, BigDecimal amount, LocalDateTime transactionDatetime,
                                   @NotNull(message = "Member Id is required.") Integer memberId) {
}
