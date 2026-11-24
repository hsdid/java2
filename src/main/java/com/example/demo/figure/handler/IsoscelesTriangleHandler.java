package com.example.demo.figure.handler;

import com.example.demo.figure.dto.response.FigureResponse;
import com.example.demo.figure.dto.response.IsoscelesTriangleResponse;
import com.example.demo.figure.exception.IncorrectParamsException;
import com.example.demo.figure.model.Figure;
import com.example.demo.figure.model.IsoscelesTriangle;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class IsoscelesTriangleHandler implements FigureHandler {
    @Override
    public Figure createFigure(Map<String, Double> params) {
        if (params.isEmpty()) {
            throw new IncorrectParamsException("Data is empty");
        }

        if (!params.containsKey("side") || !params.containsKey("base")) {
            throw new IncorrectParamsException("side or base are not defined for isosceles triangle");
        }

        Double side = params.get("side");
        Double base = params.get("base");
        if (side == null || base == null) {
            throw new IncorrectParamsException("side or base are not defined for isosceles triangle");
        }

        return new IsoscelesTriangle(side, base);
    }

    @Override
    public Class<? extends Figure> entityClass() {
        return IsoscelesTriangle.class;
    }

    @Override
    public String getType() {
        return "isoscelesTriangle";
    }

    @Override
    public FigureResponse map(Figure figure) {
        if (figure == null) {
            return null;
        }

        if (!(figure instanceof IsoscelesTriangle isoscelesTriangle)) {
            throw new IllegalArgumentException("Invalid figure type");
        }

        return new IsoscelesTriangleResponse(
                figure.getId(),
                figure.getType(),
                isoscelesTriangle.getPerimeter(),
                isoscelesTriangle.getArea(),
                isoscelesTriangle.getSide(),
                isoscelesTriangle.getBase()
        );
    }
}
