package shapesPackage;

import com.google.gson.*;

import java.lang.reflect.Type;

public class shapeSerializer implements JsonSerializer<Shape>, JsonDeserializer<Shape>{

    @Override
    public JsonElement serialize(Shape o, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Shape Type", o.getClass().getName());
        jsonObject.add("Data", jsonSerializationContext.serialize(o));
        return jsonObject;
    }

    @Override
    public Shape deserialize(JsonElement json, Type type, JsonDeserializationContext context) {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonPrimitive prim = (JsonPrimitive) jsonObject.get("Shape Type");
        String className = prim.getAsString();
        Class cls = getObjectClass(className);

        return context.deserialize(jsonObject.get("Data"), cls);
    }

    public Class getObjectClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new JsonParseException(e.getMessage());
        }
    }

}