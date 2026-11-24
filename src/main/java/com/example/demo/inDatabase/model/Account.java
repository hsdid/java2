package com.example.demo.inDatabase.model;

import com.example.demo.inMemory.exception.InsufficientFundsException;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.UUID;


@Entity
@Table(name = "accounts")
public class Account {
    @Id
    private UUID id;
    private BigDecimal balance;

    public Account() {
    }

    public Account(UUID id, BigDecimal balance) {
        this.id = id;
        this.balance = balance;
    }

    public UUID getId() {
        return id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void addBalance(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount cannot be null or less then 0");
        }

        this.balance = this.balance.add(amount);
    }

    public void subtractBalance(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }
        if (balance.compareTo(amount) < 0) {
            throw new InsufficientFundsException("Insufficient Funds");
        }

        this.balance = this.balance.subtract(amount);
    }

    @Override
    public String toString() {
        return "Account(id= " + id + " balance= " + balance;
    }
}