import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

public class converter {
    private final String fileName;
    public converter(String fileName) {
        this.fileName = fileName;
    }

    public void convertToJson(List<Student> stud) throws IOException {
        try (Writer writer = new FileWriter(fileName, false)) {
            Type type = new TypeToken<List<Student>>() {}.getType();
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .registerTypeAdapter(Student.class, new InterfaceAdapter())
                    .create();
            gson.toJson(stud, type, writer);

        } catch (IOException e) {
            throw new IOException("Error! check the file");
        }
    }

    public List<Student> convertFromJson () throws IOException {
        try (FileReader reader = new FileReader(fileName)) {
            Type type = new TypeToken<List<Student>>() {}.getType();
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .registerTypeAdapter(Student.class, new InterfaceAdapter())
                    .create();

            List<Student> studs = gson.fromJson(reader, type);
            return studs;
        } catch (IOException e) {
            throw new IOException("Error! check the file");
        }
    }

}