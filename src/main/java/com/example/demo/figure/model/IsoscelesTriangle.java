package com.example.demo.figure.model;

import com.example.demo.figure.exception.InvalidFigureException;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("isoscelesTriangle")
public class IsoscelesTriangle extends Figure {
    private double side;
    private double base;

    public IsoscelesTriangle(double side, double base) {
        if (side <= 0 || base <= 0) {
            throw new InvalidFigureException("side or base is invalid for isoscelesTriangle");
        }
        if (2 * side <= base) {
            throw new InvalidFigureException("side or base is invalid for isoscelesTriangle");
        }
        super(area(side, base), perimeter(side, base));
        this.side = side;
        this.base = base;
    }

    public IsoscelesTriangle() {
    }

    public double getSide() {
        return side;
    }

    public double getBase() {
        return base;
    }

    private static double area(double side, double base) {
        double height = Math.sqrt(side * side - (base / 2) * (base / 2));
        return base * height / 2;
    }

    private static double perimeter(double side, double base) {
        return 2 * side + base;
    }
}
