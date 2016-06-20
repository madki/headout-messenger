package models;

import apis.HeadoutApi;
import com.google.gson.Gson;
import models.headout.Category;
import models.headout.City;
import models.headout.Currency;
import models.headout.Tour;
import utils.Prices;
import utils.Urls;

import java.util.Arrays;
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
        PostbackButton button = PostbackButton.create("Select", gson.toJson(SelectCityPayload.create(city)));
        se.buttons = Collections.singletonList(button);
        return se;
    }

    public static StructuredElement fromTour(Tour tour, Currency currency, Gson gson) {
        StructuredElement se = new StructuredElement();
        se.title = tour.name;
        se.image_url = Urls.auto(tour.imageURL);
        se.subtitle = tour.neighbourhood + " | " + Prices.format(Float.valueOf(tour.price.finalPrice), currency);
        PostbackButton moreButton = PostbackButton.create("Details", gson.toJson(SelectTourPayload.create(tour.id)));
        RedirectButton webButton = RedirectButton.create("Book", HeadoutApi.WEBSITE_BASE_URL + "/tour/" + tour.id);
        se.buttons = Arrays.asList(moreButton, webButton);
        return se;
    }

    public static StructuredElement fromCategory(Category category, City city, Gson gson) {
        StructuredElement se = new StructuredElement();
        se.title = category.displayName;
        se.image_url = Urls.auto(category.imageUrl);
        se.buttons = Arrays.asList(
//                RedirectButton.create("Website", HeadoutApi.WEBSITE_BASE_URL + "/tours/" + Strings.toUrlParam(city.cityCode) + "?tags=" + Strings.join(category.tags, ",")),
                PostbackButton.create("Select", gson.toJson(SelectCategoryPayload.create(city, category)))
        );
        return se;
    }
}
