package com.example.demo.figure.handler;

import com.example.demo.figure.dto.response.CircleResponse;
import com.example.demo.figure.dto.response.FigureResponse;
import com.example.demo.figure.exception.IncorrectParamsException;
import com.example.demo.figure.model.Circle;
import com.example.demo.figure.model.Figure;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CircleHandler implements FigureHandler {

    @Override
    public Figure createFigure(Map<String, Double> params) {
        if (params.isEmpty()) {
            throw new IncorrectParamsException("Data is empty");
        }

        if (!params.containsKey("radius")) {
            throw new IncorrectParamsException("radius is not defined for circle");
        }

        Double radius = params.get("radius");

        if (radius == null) {
            throw new IncorrectParamsException("radius is not defined for circle");
        }

        return new Circle(radius);
    }

    @Override
    public Class<? extends Figure> entityClass() {
        return Circle.class;
    }

    @Override
    public String getType() {
        return "circle";
    }

    @Override
    public FigureResponse map(Figure figure) {
        if (figure == null) {
            return null;
        }

        if (!(figure instanceof Circle circle)) {
            throw new IllegalArgumentException("Invalid figure type");
        }

        return new CircleResponse(
                circle.getId(),
                circle.getType(),
                circle.getPerimeter(),
                circle.getArea(),
                circle.getRadius()
        );
    }
}
