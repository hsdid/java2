package com.example.demo.inDatabase.dto;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferRequest(
        @NotNull UUID fromAccountId,
        @NotNull UUID toAccountId,
        @NotNull @Range(min = 0, max = 1000000) BigDecimal amount
) {
}
