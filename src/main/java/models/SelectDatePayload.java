package models;

import models.CustomPayload;
import models.CustomPayloadType;

import java.time.LocalDate;

/**
 * Created by madki on 17/06/16.
 */
public class SelectDatePayload extends CustomPayload {
    private final String cityCode;
    private final LocalDate date;

    SelectDatePayload(String cityCode, LocalDate date) {
        super(CustomPayloadType.SELECT_DATE);
        this.cityCode = cityCode;
        this.date = date;
    }

    public static SelectDatePayload create(String cityCode, LocalDate date) {
        return new SelectDatePayload(cityCode, date);
    }

    public String getCityCode() {
        return cityCode;
    }

    public LocalDate getDate() {
        return date;
    }
}
