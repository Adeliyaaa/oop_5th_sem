package shapesPackage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

public class shapeConverter {
    private final String fileName;

    public shapeConverter(String fileName) {
        this.fileName = fileName;
    }

    public void convertToJson(List<Shape> shapes) throws IOException {
        try (Writer writer = new FileWriter(fileName, false)) {
            Type type = new TypeToken<List<Shape>>() {
            }.getType();
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .registerTypeAdapter(Shape.class, new shapeSerializer())
                    .create();
            gson.toJson(shapes, type, writer);

        } catch (IOException e) {
            throw new IOException("Error! check the file");
        }
    }

    public List<Shape> convertFromJson() throws IOException {
        try (FileReader reader = new FileReader(fileName)) {
            Type type = new TypeToken<List<Shape>>() {
            }.getType();
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .registerTypeAdapter(Shape.class, new shapeSerializer())
                    .create();

            List<Shape> listOfShapes = gson.fromJson(reader, type);
            return listOfShapes;
        } catch (IOException e) {
            throw new IOException("Error! check the file");
        }
    }
}

