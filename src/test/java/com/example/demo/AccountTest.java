package com.example.demo;

import com.example.demo.inMemory.model.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

public class AccountTest {
    @Test
    void shouldThrowIllegalArgumentExceptionWhenAmountIsNull() {
        Account account = new Account(UUID.randomUUID(), BigDecimal.ZERO);

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> account.subtractBalance(null));
        Assertions.assertEquals("Amount cannot be null", exception.getMessage());
    }
}
