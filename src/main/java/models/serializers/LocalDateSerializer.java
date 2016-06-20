package models.serializers;

import com.google.gson.*;
import org.joda.time.LocalDate;
import utils.Dates;

import java.lang.reflect.Type;

/**
 * Created by madki on 20/06/16.
 */
public class LocalDateSerializer implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {

    public LocalDateSerializer() {}

    @Override
    public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(Dates.DATE_FORMATTER.print(src));
    }

    @Override
    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String dateAsString = json.getAsString();
        return Dates.DATE_FORMATTER.parseLocalDate(dateAsString);
    }
}
