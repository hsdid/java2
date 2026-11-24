package com.example.demo.inDatabase.controller;

import com.example.demo.inDatabase.dto.TransferRequest;
import com.example.demo.inDatabase.dto.TransferResponse;
import com.example.demo.inDatabase.model.Transfer;
import com.example.demo.inDatabase.service.TransferService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transfers")
public class TransferController {
    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TransferResponse> transfer(@Valid @RequestBody TransferRequest transferRequest) {
        Transfer transfer = transferService.transfer(transferRequest);

        return ResponseEntity.ok(TransferResponse.from(transfer));
    }
}
