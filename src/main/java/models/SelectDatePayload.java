package models;

import models.CustomPayload;
import models.CustomPayloadType;

import java.time.LocalDate;

/**
 * Created by madki on 17/06/16.
 */
public class SelectDatePayload extends CustomPayload {
    private String cityCode;
    private LocalDate date;

    SelectDatePayload(String cityCode, LocalDate date) {
        super(CustomPayloadType.SELECT_DATE);
        this.cityCode = cityCode;
        this.date = date;
    }

    public static SelectDatePayload create(String cityCode, LocalDate date) {
        return new SelectDatePayload(cityCode, date);
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCityCode() {
        return cityCode;
    }

    public LocalDate getDate() {
        return date;
    }
}
