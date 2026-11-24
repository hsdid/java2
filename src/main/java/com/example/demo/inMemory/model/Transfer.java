package com.example.demo.inMemory.model;


import java.math.BigDecimal;
import java.util.UUID;

public class Transfer {
    private final UUID id;
    private final Account from;
    private final Account to;
    private final BigDecimal amount;

    public Transfer(UUID id, Account from, Account to, BigDecimal amount) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    public UUID getId() {
        return id;
    }

    public Account getFrom() {
        return from;
    }

    public Account getTo() {
        return to;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Transfer(id=" + id + ", from=" + from.getId() + ", to=" + to.getId() + ", amount=" + amount + ")";
    }
}
