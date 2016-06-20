package models.headout;

import com.google.gson.annotations.SerializedName;

/**
 * Created by madki on 17/06/16.
 */
public class Price {
    public String currency;
    public String originalPrice;
    public String finalPrice;
    public String minimumPayablePrice;
    public String type;
    public int bestDiscount;
    @SerializedName("otherPricesExist")
    public boolean hasOtherPrices;
}
