package com.example.demo.inDatabase.service;

import com.example.demo.inDatabase.dto.TransferRequest;
import com.example.demo.inDatabase.exception.AccountNotFoundException;
import com.example.demo.inDatabase.model.Account;
import com.example.demo.inDatabase.model.Transfer;
import com.example.demo.inDatabase.repository.AccountRepository;
import com.example.demo.inDatabase.repository.TransferRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class TransferService {
    private static final Logger log = LoggerFactory.getLogger(TransferService.class);

    private final TransferRepository transferRepository;
    private final AccountRepository accountRepository;

    public TransferService(TransferRepository transferRepository, AccountRepository accountRepository) {
        this.transferRepository = transferRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public Transfer transfer(TransferRequest request) {
        if (request == null || request.fromAccountId() == null || request.toAccountId() == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        if (request.toAccountId().equals(request.fromAccountId())) {
            throw new IllegalArgumentException("Cannot transfer to same account");
        }


        Account fromAccount;
        Account toAccount;

        if (request.toAccountId().compareTo(request.fromAccountId()) < 0) {
            toAccount = accountRepository.findAccountPessimisticLock(request.toAccountId()).orElseThrow(
                    () -> new AccountNotFoundException("Account not found")
            );
            fromAccount = accountRepository.findAccountPessimisticLock(request.fromAccountId()).orElseThrow(
                    () -> new AccountNotFoundException("Account not found")
            );
        } else {
            fromAccount = accountRepository.findAccountPessimisticLock(request.fromAccountId()).orElseThrow(
                    () -> new AccountNotFoundException("Account not found")
            );
            toAccount = accountRepository.findAccountPessimisticLock(request.toAccountId()).orElseThrow(
                    () -> new AccountNotFoundException("Account not found")
            );
        }

        Transfer transfer = new Transfer(UUID.randomUUID(), fromAccount.getId(), toAccount.getId(), request.amount());
        transferRepository.save(transfer);

        fromAccount.subtractBalance(request.amount());
        toAccount.addBalance(request.amount());

        return transfer;
    }


    @PostConstruct
    public void loadData() {
        UUID accountId1 = UUID.randomUUID();
        UUID accountId2 = UUID.randomUUID();

        accountRepository.saveAndFlush(new Account(accountId1, new BigDecimal("100")));
        accountRepository.saveAndFlush(new Account(accountId2, new BigDecimal("100")));
        log.info("AccountID=" + accountId1 + " AcccountID=" + accountId2);

    }
}
