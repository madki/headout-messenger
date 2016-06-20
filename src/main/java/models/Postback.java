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

    public SelectCityPayload selectCityPayload(Gson gson) {
        return gson.fromJson(payload, SelectCityPayload.class);
    }

    public SelectCategoryPayload selectCategoryPayload(Gson gson) {
        return gson.fromJson(payload, SelectCategoryPayload.class);
    }

    public SelectTourPayload selectTourPayload(Gson gson) {
        return gson.fromJson(payload, SelectTourPayload.class);
    }

    @Override
    public String toString() {
        return "Postback{" +
                "payload=" + Strings.toString(payload) +
                '}';
    }
}
