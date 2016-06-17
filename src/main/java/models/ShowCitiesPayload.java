package models;

/**
 * Created by madki on 17/06/16.
 */
public class ShowCitiesPayload extends CustomPayload {
    ShowCitiesPayload() {
        super(CustomPayloadType.SHOW_CITIES);
    }

    public static ShowCitiesPayload create() {
        return new ShowCitiesPayload();
    }
}
