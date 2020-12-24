import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

public class ReportConverter {
    private final String fileName;
    public ReportConverter(String fileName) {
        this.fileName = fileName;
    }

    public void convertToJson(Report report) throws IOException {
        try (Writer writer = new FileWriter(fileName, false)) {
            Type type = new TypeToken<Report>() {}.getType();
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .registerTypeAdapter(Report.class, new ReportSerializer())
                    .create();
            gson.toJson(report, type, writer);

        } catch (IOException e) {
            throw new IOException("Error! check the file");
        }
    }

    public Report convertFromJson () throws IOException {
        try (FileReader reader = new FileReader(fileName)) {
            Type type = new TypeToken<Report>() {}.getType();
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .registerTypeAdapter(Report.class, new ReportSerializer())
                    .create();

            Report report = gson.fromJson(reader, type);
            return report;
        } catch (IOException e) {
            throw new IOException("Error! check the file");
        }
    }

}