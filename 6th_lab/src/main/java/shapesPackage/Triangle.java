package shapesPackage;

public class Triangle implements Shape {

    public final double side_one;
    public final double side_two;
    public final double side_three;

    public Triangle(double side_one, double side_two, double side_three) {
        if (side_one <= 0 || side_two <= 0 || side_three <= 0)
            throw new IllegalArgumentException("Sides of a triangle cannot be less or equal to zero");

        if (side_one + side_two <= side_three || side_one + side_three <= side_two || side_two + side_three <= side_one)
            throw new IllegalArgumentException("Triangles with these sides cannot exist");

        this.side_one = side_one;
        this.side_two = side_two;
        this.side_three = side_three;
    }

    @Override
    public double calcArea() {
        //p is semiperimeter
        double p = (side_one + side_two + side_three) / 2;
        return Math.sqrt(p * (p - side_one) * (p - side_two) * (p - side_three));
    }

    @Override
    public double calcPerimeter() {
        return side_one + side_two + side_three;
    }

    public double getSide_one() {
        return side_one;
    }

    public double getSide_two() {
        return side_two;
    }

    public double getSide_three() {
        return side_three;
    }

    @Override
    public String toString() {
        return "Triangle (a = " + side_one + ", b = " + side_two + ", c = " + side_three + "):" +
                "\n area = " + calcArea() +
                "\n perimeter = " + calcPerimeter() + "\n";
    }
}