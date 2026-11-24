package com.example.demo.figure.model;

import com.example.demo.figure.exception.InvalidFigureException;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("square")
public class Square extends Figure {
    private double side;

    public Square(double side) {
        if (side <= 0) {
            throw new InvalidFigureException("Invalid side for square");
        }

        super(area(side), perimeter(side));
        this.side = side;
    }

    public Square() {

    }

    public double getSide() {
        return side;
    }

    private static double perimeter(double side) {
        return 4 * side;
    }

    private static double area(double side) {
        return side * side;
    }

    @Override
    public String toString() {
        return "Square{" +
                "side=" + side +
                '}';
    }
}
