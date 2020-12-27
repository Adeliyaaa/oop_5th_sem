import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ReportSerializer implements JsonSerializer<Report>, JsonDeserializer<Report> {
    private static final String NAME = "NAME";
    private static final String SURNAME = "SURNAME";
    private static final String LASTNAME = "LASTNAME";
    private static final String SUBJECT = "SUBJECT";
    private static final String GROUPNUMBER = "GROUPNUMBER";
    private static final String STUDENTS = "STUDENTS";

    @Override
    public JsonElement serialize(Report o, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty(NAME, o.getProfessorName());
        jsonObject.addProperty(SURNAME, o.getProfessorSurname());
        jsonObject.addProperty(LASTNAME, o.getProfessorLastname());
        jsonObject.addProperty(SUBJECT, o.getSubject());
        jsonObject.addProperty(GROUPNUMBER, o.getGroupNumber());
        jsonObject.addProperty(STUDENTS, o.getStudents().toString());

        jsonObject.add(NAME, jsonSerializationContext.serialize(o.getProfessorName()));
        jsonObject.add(SURNAME, jsonSerializationContext.serialize(o.getProfessorSurname()));
        jsonObject.add(LASTNAME, jsonSerializationContext.serialize(o.getProfessorLastname()));
        jsonObject.add(SUBJECT, jsonSerializationContext.serialize(o.getSubject()));
        jsonObject.add(GROUPNUMBER, jsonSerializationContext.serialize(o.getGroupNumber()));
        jsonObject.add(STUDENTS, jsonSerializationContext.serialize(o.getStudents()));

        return jsonObject;
    }

    @Override
    public Report deserialize(JsonElement json, Type type, JsonDeserializationContext context) {
        JsonObject jsonObject = json.getAsJsonObject();
        Report report = new Report((int)(context.deserialize(jsonObject.get(GROUPNUMBER), Integer.TYPE)),
                (String) context.deserialize(jsonObject.get(SUBJECT), String.class));


        report.setProfessor((String) context.deserialize(jsonObject.get(NAME), String.class),
                (String) context.deserialize(jsonObject.get(SURNAME), String.class),
                (String) context.deserialize(jsonObject.get(LASTNAME), String.class));

        report.setStudents((List<Student>) context.deserialize(jsonObject.get(STUDENTS),
                new TypeToken<List<Student>>(){}.getType()));

        return report;
    }

    public Class getObjectClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new JsonParseException(e.getMessage());
        }
    }
}