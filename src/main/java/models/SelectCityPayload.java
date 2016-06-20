package models;

import models.headout.City;

/**
 * Created by madki on 17/06/16.
 */
public class SelectCityPayload extends CustomPayload {
    private City city;

    private SelectCityPayload(City city) {
        super(CustomPayloadType.SELECT_CITY);
        this.city = city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public City getCity() {
        return city;
    }

    public static SelectCityPayload create(City city) {
        return new SelectCityPayload(city);
    }
}
