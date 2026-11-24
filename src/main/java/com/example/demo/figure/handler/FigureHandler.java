package com.example.demo.figure.handler;

import com.example.demo.figure.dto.FigureRequest;
import com.example.demo.figure.dto.response.FigureResponse;
import com.example.demo.figure.model.Figure;

import java.util.Map;

public interface FigureHandler {
    Figure createFigure(Map<String, Double> params);
    Class<? extends Figure> entityClass();
    String getType();
    FigureResponse map(Figure figure);
}
