package models.headout;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by madki on 20/06/16.
 */
public class TourDetail {
    public int id;
    public String name;
    public String neighbourhood;
    public String summary;
    public String highlights;
    public boolean displaySeatsLeftDisabled;
    public String additionalInfo;
    public String inclusions;
    public List<String> displayTags;
    public List<ImageUpload> imageUploads;
    public String ticketDeliveryInfo;
    @SerializedName("listingPrice") 
    public Price price;

    @Override
    public String toString() {
        return "TourDetail{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", neighbourhood='" + neighbourhood + '\'' +
                ", summary='" + summary + '\'' +
                ", highlights='" + highlights + '\'' +
                ", displaySeatsLeftDisabled=" + displaySeatsLeftDisabled +
                ", additionalInfo='" + additionalInfo + '\'' +
                ", inclusions='" + inclusions + '\'' +
                ", displayTags=" + displayTags +
                ", imageUploads=" + imageUploads +
                ", ticketDeliveryInfo='" + ticketDeliveryInfo + '\'' +
                ", price=" + price +
                '}';
    }
}
