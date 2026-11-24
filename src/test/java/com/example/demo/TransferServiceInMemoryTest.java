package com.example.demo;

import com.example.demo.inMemory.model.Account;
import com.example.demo.inMemory.model.Transfer;
import com.example.demo.inMemory.service.TransferService;
import com.example.demo.inMemory.exception.InsufficientFundsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TransferServiceInMemoryTest {
    private final TransferService transferService = new TransferService();

    @Test
    void shouldTransfer() throws InterruptedException {
        //given
        Account from = new Account(UUID.randomUUID(), new BigDecimal("100"));
        Account to = new Account(UUID.randomUUID(), new BigDecimal("100"));
        Transfer transfer = new Transfer(UUID.randomUUID(), from, to, new BigDecimal("50"));
        //when
        transferService.transfer(transfer);
        //then
        Assertions.assertEquals(0, from.getBalance().compareTo(new BigDecimal("50")));
        Assertions.assertEquals(0, to.getBalance().compareTo(new BigDecimal("150")));
    }

    @Test
    void shouldThrowInsufficientFundsExceptionWhenAccountHaveNoFounds() throws InterruptedException {

        //given
        Account from = new Account(UUID.randomUUID(), new BigDecimal("100"));
        Account to = new Account(UUID.randomUUID(), new BigDecimal("100"));
        Transfer transfer = new Transfer(UUID.randomUUID(), from, to, new BigDecimal("150"));
        //when
        //then
        Exception exception = Assertions.assertThrows(
                InsufficientFundsException.class,
                () -> transferService.transfer(transfer)
        );

        Assertions.assertEquals("Insufficient Funds", exception.getMessage());
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenTransferToSameAccount() throws InterruptedException {

        //given
        Account to = new Account(UUID.randomUUID(), new BigDecimal("100"));
        Transfer transfer = new Transfer(UUID.randomUUID(), to, to, new BigDecimal("10"));
        //when
        //then
        Exception exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> transferService.transfer(transfer)
        );

        Assertions.assertEquals("Cannot transfer to same account", exception.getMessage());
    }

    @Test
    void shouldMaintainConsistentBalanceUnderHighConcurrency() throws InterruptedException {
        Account accountA = new Account(UUID.randomUUID(), new BigDecimal("10000"));
        Account accountB = new Account(UUID.randomUUID(), new BigDecimal("10000"));

        ExecutorService executorService = Executors.newFixedThreadPool(20);

        for (int i = 0; i < 100; i++) {
            executorService.submit(() -> {
                Transfer transfer = new Transfer(UUID.randomUUID(), accountA, accountB, new BigDecimal("100"));
                try {
                    transferService.transfer(transfer);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        executorService.awaitTermination(5, TimeUnit.SECONDS);

        Assertions.assertEquals(0, accountA.getBalance().compareTo(BigDecimal.ZERO));
        Assertions.assertEquals(0, accountB.getBalance().compareTo(new BigDecimal("20000")));
    }
}
