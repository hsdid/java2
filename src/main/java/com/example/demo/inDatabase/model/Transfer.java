package com.example.demo.inDatabase.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "transfers")
public class Transfer {
    @Id
    private UUID id;
    private UUID fromId;
    private UUID toId;
    private BigDecimal amount;

    public Transfer() {
    }

    public Transfer(UUID id, UUID from, UUID to, BigDecimal amount) {
        this.id = id;
        this.fromId = from;
        this.toId = to;
        this.amount = amount;
    }

    public UUID getId() {
        return id;
    }

    public UUID getFromId() {
        return fromId;
    }

    public UUID getToId() {
        return toId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Transfer(id=" + id + ", from=" + fromId + ", to=" + toId + ", amount=" + amount + ")";
    }
}
