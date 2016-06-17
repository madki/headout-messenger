package models;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import utils.Strings;

/**
 * Created by madki on 17/06/16.
 */
public class Attachment {
    private String type;
    private JsonElement payload;

    public void setType(String type) {
        this.type = type;
    }

    public void setPayload(JsonElement payload) {
        this.payload = payload;
    }

    public String getType() {
        return type;
    }

    public JsonElement getPayload() {
        return payload;
    }

    public static Attachment withButtons(ButtonsPayload buttons, Gson gson) {
        Attachment a = new Attachment();
        a.setType("template");
        a.setPayload(gson.toJsonTree(buttons));
        return a;
    }

    public static Attachment withElements(GenericPayload payload, Gson gson) {
        Attachment a = new Attachment();
        a.setType("template");
        a.setPayload(gson.toJsonTree(payload));
        return a;
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "type='" + type + '\'' +
                ", payload=" + Strings.toString(payload) +
                '}';
    }
}
