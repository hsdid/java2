package com.example.demo.figure.dto.response;

public class SquareResponse extends FigureResponse {

    private final double side;

    public SquareResponse(Long id, String type, double perimeter, double area, double side) {
        super(id, type, perimeter, area);
        this.side = side;
    }

    public double getSide() {
        return side;
    }

    @Override
    public String toString() {
        return "SquareResponse [side=" + side + "]";
    }
}
