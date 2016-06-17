package models;

/**
 * Created by madki on 17/06/16.
 */
public class SelectCityPayload extends CustomPayload {
    private final String cityCode;

    private SelectCityPayload(String cityCode) {
        super(CustomPayloadType.SELECT_CITY);
        this.cityCode = cityCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public static SelectCityPayload create(String cityCode) {
        return new SelectCityPayload(cityCode);
    }
}
