package com.huda.money_track.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionRecordDto(String description, BigDecimal amount, LocalDateTime transactionDatetime, Integer memberId) {
}
