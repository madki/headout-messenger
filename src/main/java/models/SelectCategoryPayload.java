package models;

import models.headout.Category;
import models.headout.City;
import utils.Strings;

/**
 * Created by madki on 17/06/16.
 */
public class SelectCategoryPayload extends CustomPayload {
    private String cityCode;
    private String cityName;
    private Category category;

    private SelectCategoryPayload(City city, Category category) {
        super(CustomPayloadType.SELECT_CATEGORY);
        this.cityName = city.displayName;
        this.cityCode = city.cityCode;
        this.category = category;
    }

    public static SelectCategoryPayload create(City city, Category category) {
        return new SelectCategoryPayload(city, category);
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
