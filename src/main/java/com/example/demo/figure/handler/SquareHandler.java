package com.example.demo.figure.handler;

import com.example.demo.figure.dto.response.FigureResponse;
import com.example.demo.figure.dto.response.SquareResponse;
import com.example.demo.figure.exception.IncorrectParamsException;
import com.example.demo.figure.model.Figure;
import com.example.demo.figure.model.Square;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SquareHandler implements FigureHandler {
    @Override
    public Figure createFigure(Map<String, Double> params) {

        if (params.isEmpty()) {
            throw new IncorrectParamsException("Data is empty");
        }

        if (!params.containsKey("side")) {
            throw new IncorrectParamsException("side is not defined for square");
        }

        Double side = params.get("side");
        if (side == null) {
            throw new IncorrectParamsException("side is not defined for square");
        }


        return new Square(side);
    }

    @Override
    public Class<? extends Figure> entityClass() {
        return Square.class;
    }

    @Override
    public String getType() {
        return "square";
    }

    @Override
    public FigureResponse map(Figure figure) {
        if (figure == null) {
            return null;
        }

        if (!(figure instanceof Square square)) {
            throw new IllegalArgumentException("Invalid figure type");
        }

        return new SquareResponse(
                square.getId(),
                square.getType(),
                square.getPerimeter(),
                square.getArea(),
                square.getSide()
        );
    }
}
