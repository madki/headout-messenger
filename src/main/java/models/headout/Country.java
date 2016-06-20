package models.headout;

import com.google.gson.annotations.SerializedName;

/**
 * Created by madki on 17/06/16.
 */
public class Country {
    public String code;

    @SerializedName("displayName")
    public String name;

    public Currency currency;

}
