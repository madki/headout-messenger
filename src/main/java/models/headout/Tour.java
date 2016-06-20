package models.headout;

import com.google.gson.annotations.SerializedName;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.util.List;

/**
 * Created by madki on 17/06/16.
 */
public class Tour {
    public int id;

    public String name;

    public String imageURL;

    public String neighbourhood;

    @SerializedName("primaryCategoryDisplayName")
    public String categoryName;

    @SerializedName("tourType")
    public String type;

    @SerializedName("tourUrl")
    public String url;

//    @SerializedName("inventoryDates")
//    public List<LocalDate> dates;

//    @SerializedName("inventoryTimes")
//    public List<LocalTime> times;

    @SerializedName("listingPrice")
    public Price price;

    @SerializedName("otherPricesExist")
    public boolean hasOtherPrices;

    @SerializedName("bestDiscount")
    public float discount;

    public String callToAction;

}
