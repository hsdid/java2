package com.example.demo.figure.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Map;

public record FigureRequest(@NotBlank String type, @NotNull Map<String, Double> params) {
}
