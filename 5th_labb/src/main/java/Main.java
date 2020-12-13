import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static java.lang.System.out;

public class Main {

    public static void main(String[] args) {
        List<Shape> listOfShapes = new ArrayList<>();
        listOfShapes.add(new Circles(5));
        listOfShapes.add(new Triangle(3, 4, 5 ));
        listOfShapes.add(new Square(6));
        listOfShapes.add(new Square (4));
        listOfShapes.add(new Rect (4, 8));

        shapeConverter converter = new shapeConverter("shapeList.json");
        try {converter.convertToJson(listOfShapes);}
        catch (IOException e) {e.printStackTrace();}

            try {
                List<Shape> readShapes = converter.convertFromJson();
                readShapes.forEach(shape -> out.println(shape.toString()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

}
