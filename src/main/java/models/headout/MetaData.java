package models.headout;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by madki on 17/06/16.
 */
public class MetaData {
    public List<LocalDate> availableDates;

    public List<LocalDate> searchedDates;

    public List<String> neighbourhoodList;

    public Float minPrice;

    public Float maxPrice;

    public List<String> experienceTypes;

    public City city;

    @SerializedName("categoryCity")
    public Category category;


    public static class City {
        public String code;

        public String displayName;

        public Country country;
    }

    public Currency currency() {
        return city.country.currency;
    }

}
