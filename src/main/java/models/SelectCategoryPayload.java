package models;

import models.headout.Category;
import models.headout.City;

/**
 * Created by madki on 17/06/16.
 */
public class SelectCategoryPayload extends CustomPayload {
    private final City city;
    private final Category category;

    SelectCategoryPayload(City city, Category category) {
        super(CustomPayloadType.SELECT_CATEGORY);
        this.city = city;
        this.category = category;
    }

    public static SelectCategoryPayload create(City city, Category category) {
        return new SelectCategoryPayload(city, category);
    }

    public City getCity() {
        return city;
    }

    public Category getCategory() {
        return category;
    }
}
