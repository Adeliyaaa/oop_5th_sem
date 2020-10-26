package com.company;

public class Rect implements Shape {

    public final double width;
    public final double length;

    public Rect(double width, double length) {
        if (width <=0 || length <= 0)
            throw new IllegalArgumentException("Width and length of a rectangle cannot be less or equal to zero");

        this.width = width;
        this.length = length;
    }

    @Override
    public double calcArea() {
        return width * length;
    }

    @Override
    public double calcPerimeter() {
        return 2 * (width + length);
    }

    public double getWidth() {
        return width;
    }

    public double getLength() {
        return length;
    }

    @Override
    public String toString() {
        return "Rectangle (width = " + width + ", length = " + length + "):" +
                "\n area = " + calcArea() +
                "\n perimeter = " + calcPerimeter() + "\n";
    }
}
