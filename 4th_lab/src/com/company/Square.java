package com.company;

public class Square implements Shape {

    public final double side;

    public Square(double side) {
        if (side <= 0)
            throw new IllegalArgumentException("Side of a square cannot be less or equal to zero");

        this.side = side;
    }

    @Override
    public double calcArea() {
        return side * side;
    }

    @Override
    public double calcPerimeter() {
        return 4 * side;
    }

    public double getSide() {
        return side;
    }

    @Override
    public String toString() {
        return "Square (side = " + side + "):" +
                "\n area = " + calcArea() +
                "\n perimeter = " + calcPerimeter() + "\n";
    }
}
