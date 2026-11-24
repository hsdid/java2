package com.example.demo.figure.dto.response;

public class IsoscelesTriangleResponse extends FigureResponse{
    private final double side;
    private final double base;

    public IsoscelesTriangleResponse(Long id, String type, double perimeter, double area, double side, double base) {
        super(id, type, perimeter, area);
        this.side = side;
        this.base = base;
    }

    public double getSide() {
        return side;
    }

    public double getBase() {
        return base;
    }
}
