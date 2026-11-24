package com.example.demo.figure.model;

import com.example.demo.figure.exception.InvalidFigureException;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("circle")
public class Circle extends Figure {
    private double radius;

    public Circle(double radius) {
        if (radius <= 0 ) {
            throw new InvalidFigureException("Invalid radius for circle");
        }
        super(area(radius), perimeter(radius));
        this.radius = radius;
    }

    public Circle() {

    }

    public double getRadius() {
        return radius;
    }

    private static double area(double radius) {
        return Math.PI * Math.pow(radius, 2);
    }

    private static double perimeter(double radius) {
        return 2 * Math.PI * radius;
    }

    @Override
    public String toString() {
        return "Circle{" +
                "radius=" + radius +
                '}';
    }
}
