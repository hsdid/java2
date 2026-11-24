package com.example.demo.inMemory.service;

import com.example.demo.inMemory.model.Account;
import com.example.demo.inMemory.model.Transfer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;

public class TransferService {
    private static final Logger log = LoggerFactory.getLogger(TransferService.class);
    private final ConcurrentMap<UUID, ReentrantLock> lockConcurrentMap = new ConcurrentHashMap<>();

    public ReentrantLock getLockForAccount(UUID accountId) {
        return lockConcurrentMap.computeIfAbsent(accountId, id -> new ReentrantLock());
    }

    public void transfer(Transfer transfer) throws InterruptedException {
        if (transfer.getFrom() == transfer.getTo()) {
            throw new IllegalArgumentException("Cannot transfer to same account");
        }

        // always lock smaller id first
        Account firstLockAccount = transfer.getFrom().getId().compareTo(transfer.getTo().getId()) < 0 ? transfer.getFrom() : transfer.getTo();
        Account secondLockAccount = firstLockAccount == transfer.getTo() ? transfer.getFrom() : transfer.getTo();

        ReentrantLock firstLock = getLockForAccount(firstLockAccount.getId());
        ReentrantLock secondLock = getLockForAccount(secondLockAccount.getId());

        firstLock.lock();
        secondLock.lock();
        try {
            log.info("[TRYLOCK] " + Thread.currentThread().getName() + " Lock");
            log.info("Process: " + transfer);
            Account from = transfer.getFrom();
            Account to = transfer.getTo();

            from.subtractBalance(transfer.getAmount());
            to.addBalance(transfer.getAmount());
        } finally {
            firstLock.unlock();
            secondLock.unlock();

            log.info("[TRYLOCK] " + Thread.currentThread().getName() + " Unlock");
        }
    }
}
