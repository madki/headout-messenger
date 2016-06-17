package models;

import com.fasterxml.jackson.databind.JsonNode;
import utils.Strings;

/**
 * Created by madki on 17/06/16.
 */
public class Postback {
    private JsonNode payload;

    public JsonNode getPayload() {
        return payload;
    }

    public void setPayload(JsonNode payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "Postback{" +
                "payload=" + Strings.toString(payload) +
                '}';
    }
}
