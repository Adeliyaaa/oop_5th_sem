package com.company;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import static java.lang.System.out;

public class ShapeAccumulator {
    List <Shape> ShapesList;

    public ShapeAccumulator(){
        ShapesList = new ArrayList<>();
    }

    public <T extends Shape> void add (T shape){
        ShapesList.add(shape);
    }

    public void addAll (Collection<? extends Shape> allFigures){
        ShapesList.addAll(allFigures);
    }

    public Shape getMaxAreaShape(){
        ShapesList.sort(Comparator.comparingDouble(Shape::calcArea));
        return ShapesList.get(ShapesList.size()-1);
    }

    public Shape getMinAreaShape(){
        ShapesList.sort(Comparator.comparingDouble(Shape::calcArea));
        return ShapesList.get(0);
    }

    public Shape getMaxPerimeterShape(){
        ShapesList.sort(Comparator.comparingDouble(Shape::calcPerimeter));
        return ShapesList.get(ShapesList.size()-1);
    }

    public Shape getMinPerimeterShape(){
        ShapesList.sort(Comparator.comparingDouble(Shape::calcPerimeter));
        return ShapesList.get(0);
    }

    public double getTotalArea(){
        return ShapesList.stream().mapToDouble(Shape::calcArea).sum();
    }

    public double getTotalPerimeter(){
        return ShapesList.stream().mapToDouble(Shape::calcPerimeter).sum();
    }

}
