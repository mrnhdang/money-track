package com.huda.money_track.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class ErrorMessage {
    private final String message;
    private final HttpStatus httpStatus;
    private final LocalDate localDate;
}
