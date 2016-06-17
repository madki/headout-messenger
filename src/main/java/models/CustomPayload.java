package models;

/**
 * Created by madki on 17/06/16.
 */
public class CustomPayload {
    private final CustomPayloadType type;

    CustomPayload(CustomPayloadType type) {
        this.type = type;
    }

    public CustomPayloadType getType() {
        return type;
    }
}
