package com.example.demo.figure.dto.response;

import com.example.demo.figure.model.Figure;

public class FigureResponse {
    private final Long id;
    private final String type;
    private final double perimeter;
    private final double area;

    public FigureResponse(Long id, String type, double perimeter, double area) {
        this.id = id;
        this.type = type;
        this.perimeter = perimeter;
        this.area = area;
    }

    public String getType() {
        return type;
    }

    public Long getId() {
        return id;
    }

    public double getPerimeter() {
        return perimeter;
    }

    public double getArea() {
        return area;
    }
}
