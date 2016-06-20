package models.headout;

import apis.HeadoutApi;
import retrofit2.Call;
import utils.Strings;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by madki on 17/06/16.
 */
public final class TourQuery {

    private String cityCode;

    private List<LocalDate> dates;

    private List<TourQuery.Time> times;

    private List<String> neighbourhoods;

    private List<String> experienceTypes;

    private Float minPrice;

    private Float maxPrice;

    private TourQuery.SortType sortType;

    private TourQuery.SortOrder sortOrder;

    private List<String> tags;

    private Integer categoryId;

    private TourQuery() {}
    
    private TourQuery(
            String cityCode,
            List<LocalDate> dates,
            List<TourQuery.Time> times,
            List<String> neighbourhoods,
            List<String> experienceTypes,
            Float minPrice,
            Float maxPrice,
            TourQuery.SortType sortType,
            TourQuery.SortOrder sortOrder,
            List<String> tags,
            Integer categoryId) {
        if (cityCode == null) {
            throw new NullPointerException("Null cityCode");
        }
        this.cityCode = cityCode;
        this.dates = dates;
        this.times = times;
        this.neighbourhoods = neighbourhoods;
        this.experienceTypes = experienceTypes;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.sortType = sortType;
        this.sortOrder = sortOrder;
        this.tags = tags;
        this.categoryId = categoryId;
    }

    
    public String cityCode() {
        return cityCode;
    }

    
    
    public List<LocalDate> dates() {
        return dates;
    }

    
    
    public List<TourQuery.Time> times() {
        return times;
    }

    
    
    public List<String> neighbourhoods() {
        return neighbourhoods;
    }

    
    
    public List<String> experienceTypes() {
        return experienceTypes;
    }

    
    
    public Float minPrice() {
        return minPrice;
    }

    
    
    public Float maxPrice() {
        return maxPrice;
    }

    
    
    public TourQuery.SortType sortType() {
        return sortType;
    }

    
    
    public TourQuery.SortOrder sortOrder() {
        return sortOrder;
    }

    
    
    public List<String> tags() {
        return tags;
    }

    
    
    public Integer categoryId() {
        return categoryId;
    }

    
    public String toString() {
        return "TourQuery{"
                + "cityCode=" + cityCode + ", "
                + "dates=" + dates + ", "
                + "times=" + times + ", "
                + "neighbourhoods=" + neighbourhoods + ", "
                + "experienceTypes=" + experienceTypes + ", "
                + "minPrice=" + minPrice + ", "
                + "maxPrice=" + maxPrice + ", "
                + "sortType=" + sortType + ", "
                + "sortOrder=" + sortOrder + ", "
                + "tags=" + tags + ", "
                + "categoryId=" + categoryId
                + "}";
    }

    
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof TourQuery) {
            TourQuery that = (TourQuery) o;
            return (this.cityCode.equals(that.cityCode()))
                    && ((this.dates == null) ? (that.dates() == null) : this.dates.equals(that.dates()))
                    && ((this.times == null) ? (that.times() == null) : this.times.equals(that.times()))
                    && ((this.neighbourhoods == null) ? (that.neighbourhoods() == null) : this.neighbourhoods.equals(that.neighbourhoods()))
                    && ((this.experienceTypes == null) ? (that.experienceTypes() == null) : this.experienceTypes.equals(that.experienceTypes()))
                    && ((this.minPrice == null) ? (that.minPrice() == null) : this.minPrice.equals(that.minPrice()))
                    && ((this.maxPrice == null) ? (that.maxPrice() == null) : this.maxPrice.equals(that.maxPrice()))
                    && ((this.sortType == null) ? (that.sortType() == null) : this.sortType.equals(that.sortType()))
                    && ((this.sortOrder == null) ? (that.sortOrder() == null) : this.sortOrder.equals(that.sortOrder()))
                    && ((this.tags == null) ? (that.tags() == null) : this.tags.equals(that.tags()))
                    && ((this.categoryId == null) ? (that.categoryId() == null) : this.categoryId.equals(that.categoryId()));
        }
        return false;
    }

    
    public int hashCode() {
        int h = 1;
        h *= 1000003;
        h ^= cityCode.hashCode();
        h *= 1000003;
        h ^= (dates == null) ? 0 : dates.hashCode();
        h *= 1000003;
        h ^= (times == null) ? 0 : times.hashCode();
        h *= 1000003;
        h ^= (neighbourhoods == null) ? 0 : neighbourhoods.hashCode();
        h *= 1000003;
        h ^= (experienceTypes == null) ? 0 : experienceTypes.hashCode();
        h *= 1000003;
        h ^= (minPrice == null) ? 0 : minPrice.hashCode();
        h *= 1000003;
        h ^= (maxPrice == null) ? 0 : maxPrice.hashCode();
        h *= 1000003;
        h ^= (sortType == null) ? 0 : sortType.hashCode();
        h *= 1000003;
        h ^= (sortOrder == null) ? 0 : sortOrder.hashCode();
        h *= 1000003;
        h ^= (tags == null) ? 0 : tags.hashCode();
        h *= 1000003;
        h ^= (categoryId == null) ? 0 : categoryId.hashCode();
        return h;
    }

    public static TourQuery.Builder builder() {
        return new Builder();
    }

    public Call<TourListResponse> fetch(HeadoutApi headoutApi) {
        return headoutApi.getTours(cityCode(), categoryId(), Strings.join(tags(), ","), sortType(), sortOrder(), dates(), times(), neighbourhoods(), minPrice(), maxPrice());
    }

    public TourQuery.Builder toBuilder() {
        return new Builder(this);
    }

    public static final class Builder {
        private String cityCode;
        private List<LocalDate> dates;
        private List<TourQuery.Time> times;
        private List<String> neighbourhoods;
        private List<String> experienceTypes;
        private Float minPrice;
        private Float maxPrice;
        private TourQuery.SortType sortType;
        private TourQuery.SortOrder sortOrder;
        private List<String> tags;
        private Integer categoryId;
        Builder() {
        }
        Builder(TourQuery source) {
            cityCode(source.cityCode());
            dates(source.dates());
            times(source.times());
            neighbourhoods(source.neighbourhoods());
            experienceTypes(source.experienceTypes());
            minPrice(source.minPrice());
            maxPrice(source.maxPrice());
            sortType(source.sortType());
            sortOrder(source.sortOrder());
            tags(source.tags());
            categoryId(source.categoryId());
        }
        
        public TourQuery.Builder cityCode(String cityCode) {
            this.cityCode = cityCode;
            return this;
        }
        
        public TourQuery.Builder dates(List<LocalDate> dates) {
            this.dates = dates;
            return this;
        }
        
        public TourQuery.Builder times(List<TourQuery.Time> times) {
            this.times = times;
            return this;
        }
        
        public TourQuery.Builder neighbourhoods(List<String> neighbourhoods) {
            this.neighbourhoods = neighbourhoods;
            return this;
        }
        
        public TourQuery.Builder experienceTypes(List<String> experienceTypes) {
            this.experienceTypes = experienceTypes;
            return this;
        }
        
        public TourQuery.Builder minPrice(Float minPrice) {
            this.minPrice = minPrice;
            return this;
        }
        
        public TourQuery.Builder maxPrice(Float maxPrice) {
            this.maxPrice = maxPrice;
            return this;
        }
        
        public TourQuery.Builder sortType(TourQuery.SortType sortType) {
            this.sortType = sortType;
            return this;
        }

        public TourQuery.Builder sortOrder(TourQuery.SortOrder sortOrder) {
            this.sortOrder = sortOrder;
            return this;
        }

        public TourQuery.Builder tags(List<String> tags) {
            this.tags = tags;
            return this;
        }

        public TourQuery.Builder categoryId(Integer categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public TourQuery build() {
            TourQuery result = new TourQuery(
                    this.cityCode,
                    this.dates,
                    this.times,
                    this.neighbourhoods,
                    this.experienceTypes,
                    this.minPrice,
                    this.maxPrice,
                    this.sortType,
                    this.sortOrder,
                    this.tags,
                    this.categoryId);
            return result;
        }
    }

    public enum SortType {
        PRICE("PRICE");

        private final String value;

        SortType(String value) {
            this.value = value;
        }

        
        public String toString() {
            return value;
        }
    }

    public enum SortOrder {
        ASC("ASC"),
        DESC("DESC");
        private final String value;

        SortOrder(String value) {
            this.value = value;
        }

        
        public String toString() {
            return value;
        }
    }

    public enum Time {
        MORNING("MORNING"),
        NOON("NOON"),
        EVENING("EVENING"),
        NIGHT("NIGHT");

        private final String value;

        Time(String value) {
            this.value = value;
        }

        
        public String toString() {
            return value;
        }
    }
}
