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
        ShapesList.add(new Rect(9, 15));

        List <Circles> circlesList = new ArrayList<>();
        circlesList.add(new Circles (5));
        circlesList.add(new Circles(8));
        circlesList.add(new Circles(10));

        List <Square> squareList = new ArrayList<>();
        squareList.add(new Square(4));
        squareList.add(new Square(9));
        squareList.add(new Square(5));

        ShapeAccumulator shapeAccumulator = new ShapeAccumulator();
        shapeAccumulator.addAll(ShapesList);
        shapeAccumulator.addAll(circlesList);
        shapeAccumulator.addAll(squareList);
        shapeAccumulator.add(new Triangle(24, 23, 22));

        out.println("Maximum Area is:");
        out.println(shapeAccumulator.getMaxAreaShape().toString());
        out.println("Maximum Perimeter is:");
        out.println(shapeAccumulator.getMaxPerimeterShape());
        out.println("Total Area is:");
        out.println(shapeAccumulator.getTotalArea());
        out.println("Total Perimeter is");
        out.println(shapeAccumulator.getTotalPerimeter());

    }
}



