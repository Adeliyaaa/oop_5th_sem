import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

public class InterfaceAdapter implements JsonSerializer<Student>, JsonDeserializer<Student> {
    private static final String NAME = "NAME";
    private static final String SURNAME = "SURNAME";
    private static final String LASTNAME = "LASTNAME";
    private static final String GRADE = "GRADE";

    @Override
    public JsonElement serialize(Student o, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty(NAME, o.getName());
        jsonObject.addProperty(SURNAME, o.getSurname());
        jsonObject.addProperty(LASTNAME, o.getLastname());
        jsonObject.addProperty(GRADE, o.getGrade());

        jsonObject.add(NAME, jsonSerializationContext.serialize(o.getName()));
        jsonObject.add(SURNAME, jsonSerializationContext.serialize(o.getSurname()));
        jsonObject.add(LASTNAME, jsonSerializationContext.serialize(o.getLastname()));
        jsonObject.add(GRADE, jsonSerializationContext.serialize(o.getGrade()));

        return jsonObject;
    }

    @Override
    public Student deserialize(JsonElement json, Type type, JsonDeserializationContext context) {
        JsonObject jsonObject = json.getAsJsonObject();
     //   JsonPrimitive prim = (JsonPrimitive) jsonObject.get(GRADE);
       // String grade = prim.getAsString();
        //Class clazz = getObjectClass(grade);

        Student stud = new Student(context.deserialize(jsonObject.get(NAME), String.class),
                context.deserialize(jsonObject.get(SURNAME), String.class),
                context.deserialize(jsonObject.get(LASTNAME), String.class));
        stud.addGrade(context.deserialize(jsonObject.get(GRADE), int.class));

        return stud;
    }

    public Class getObjectClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new JsonParseException(e.getMessage());
        }
    }
}