package shapesPackage;

public class Circles implements Shape {

    public final double radius;

    public Circles(double radius) {
        if (radius <= 0)
            throw new IllegalArgumentException("Radius cannot be less or equal to zero");
        this.radius = radius;
    }

    @Override
    public double calcArea() {
        return Math.PI * Math.pow(radius, 2);
    }

    @Override
    public double calcPerimeter() {
        return 2 * Math.PI * radius;
    }

    public double getRadius(){
        return radius;
    }

    @Override
    public String toString() {
        return "Circle (R = " + radius + "):" +
                "\n area = " + calcArea() +
                "\n perimeter = " + calcPerimeter() + "\n";
    }
}