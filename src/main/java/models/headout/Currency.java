package models.headout;

import com.google.gson.annotations.SerializedName;

/**
 * Created by madki on 17/06/16.
 */
public class Currency {
    public String code;
    @SerializedName("currencyName")
    public String name;
    public String symbol;
    public String localSymbol;
    public int precision;
}
