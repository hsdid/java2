package com.example.demo.inDatabase.repository;

import com.example.demo.inDatabase.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
}
