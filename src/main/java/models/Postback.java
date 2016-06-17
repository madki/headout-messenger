package models;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import utils.Strings;

/**
 * Created by madki on 17/06/16.
 */
public class Postback {
    private String payload;

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public CustomPayloadType getPayloadType(Gson gson) {
        CustomPayload pl = gson.fromJson(getPayload(), CustomPayload.class);
        return pl.getType();
    }

    @Override
    public String toString() {
        return "Postback{" +
                "payload=" + Strings.toString(payload) +
                '}';
    }
}
