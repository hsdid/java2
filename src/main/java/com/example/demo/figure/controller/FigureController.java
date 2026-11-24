package com.example.demo.figure.controller;


import com.example.demo.figure.dto.FigureRequest;
import com.example.demo.figure.dto.response.FigureResponse;
import com.example.demo.figure.model.Figure;
import com.example.demo.figure.service.FigureService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/figures")
public class FigureController {

    private final FigureService figureService;

    public FigureController(FigureService figureService) {
        this.figureService = figureService;
    }

    @PostMapping
    public ResponseEntity<FigureResponse> createFigure(@Valid @RequestBody FigureRequest figureRequest) {

        FigureResponse figure = figureService.createFigure(figureRequest);
        return ResponseEntity.ok(figure);
    }

    @GetMapping
    public ResponseEntity<Page<FigureResponse>> getFigure(
            @RequestParam(required = false) String type,
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return ResponseEntity.ok(figureService.getFigures(pageable, type, filters));
    }
}
