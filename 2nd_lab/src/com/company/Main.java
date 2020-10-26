package com.company;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import static java.lang.System.out;

public class Main {

    public static void main(String[] args) {

        List <Shape> ShapesList = new ArrayList<>();

        ShapesList.add(new Circles(7));
        ShapesList.add(new Triangle(3, 4, 5));
        ShapesList.add(new Rect(5, 8));
        ShapesList.add(new Square(15));
        ShapesList.add(new Triangle(12, 17, 14));
        ShapesList.add(new Circles(14));
        ShapesList.add(new Rect(9, 15));

        out.println("The list of shapes we have:");
        ShapesList.forEach(shape -> out.println(shape.toString()));

        //sorting by perimeter
        ShapesList.sort(Comparator.comparingDouble(Shape::calcPerimeter));
        out.println("The shape with the largest perimeter is:");
        out.println(ShapesList.get(ShapesList.size()-1).toString());
        out.println("The shape with the smallest perimeter is:");
        out.println(ShapesList.get(0).toString());

        //sorting by area
        ShapesList.sort(Comparator.comparingDouble(Shape::calcArea));
        out.println("The shape with the largest area is:");
        out.println(ShapesList.get(ShapesList.size()-1).toString());
        out.println("The shape with the smallest area is:");
        out.println(ShapesList.get(0).toString());

        //calculating total area
        double Area = ShapesList.stream().mapToDouble(Shape::calcArea).sum();
        out.println("Total area of the shapes is " + Area);

    }
}
