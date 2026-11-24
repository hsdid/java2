package com.example.demo.figure.dto.response;

public class CircleResponse extends FigureResponse {
    private final double radius;

    public CircleResponse(Long id, String type, double perimeter, double area, double radius) {
        super(id, type, perimeter, area);
        this.radius = radius;
    }
    public double getRadius() {
        return radius;
    }

}
