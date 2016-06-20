package models;

import models.headout.Category;
import models.headout.City;

/**
 * Created by madki on 17/06/16.
 */
public class SelectCategoryPayload extends CustomPayload {
    private String cityName;
    private Category category;

    private SelectCategoryPayload(City city, Category category) {
        super(CustomPayloadType.SELECT_CATEGORY);
        this.cityName = city.displayName;
        this.category = category;
    }

    public static SelectCategoryPayload create(City city, Category category) {
        return new SelectCategoryPayload(city, category);
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
