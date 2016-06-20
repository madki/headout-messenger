package models.serializers;

import com.google.gson.*;
import org.joda.time.LocalTime;
import utils.Times;

import java.lang.reflect.Type;

/**
 * Created by madki on 20/06/16.
 */
public class LocalTimeSerializer implements JsonSerializer<LocalTime>, JsonDeserializer<LocalTime> {

    public LocalTimeSerializer() {}

    @Override
    public LocalTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String timeAsString = json.getAsString();
        return Times.TIME_FORMATTER.parseLocalTime(timeAsString);
    }

    @Override
    public JsonElement serialize(LocalTime src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(Times.TIME_FORMATTER.print(src));
    }
}
