package com.example.demo.inDatabase.dto;


import com.example.demo.inDatabase.model.Transfer;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferResponse(UUID id, UUID fromAccountId, UUID toAccountId, BigDecimal amount) {

    public static TransferResponse from(Transfer transfer) {
        return new TransferResponse(
                transfer.getId(),
                transfer.getFromId(),
                transfer.getToId(),
                transfer.getAmount()
        );

    }
}
