package models;

import com.google.gson.Gson;
import models.headout.City;
import utils.Urls;

import java.util.Collections;
import java.util.List;

/**
 * Created by madki on 17/06/16.
 */
public class StructuredElement {
    public String title;
    public String image_url;
    public String subtitle;
    public List<Button> buttons;

    public static StructuredElement fromCity(City city, Gson gson) {
        StructuredElement se = new StructuredElement();
        se.title = city.displayName;
        se.image_url = Urls.auto(city.imageURL);
        se.subtitle = city.tagLine;
        PostbackButton button = PostbackButton.create("Select", gson.toJson(SelectCityPayload.create(city.cityCode)));
        se.buttons = Collections.singletonList(button);
        return se;
    }
}
