package apis;

import models.headout.Category;
import models.headout.City;
import models.headout.TourListResponse;
import models.headout.TourQuery;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by madki on 17/06/16.
 */
public interface HeadoutApi {
    public static final String WEBSITE_BASE_UTL = "https://www.headout.com";

    @GET("api/v2/city/list")
    Call<List<City>> getCities();

    @GET("api/v2/category-city/list/city/{city}")
    Call<List<Category>> getCategories(@Path("city") String cityCode);

    @GET("api/v2/tour-group/list/city/{city}")
    Call<TourListResponse> getTours(@Path("city") String cityCode,
                                          @Query("categoryId") Integer categoryId,
                                          @Query("tags") String tags,
                                          @Query("sort-type") TourQuery.SortType sortType,
                                          @Query("sort-order") TourQuery.SortOrder sortOrder,
                                          @Query("filter-dates[]") List<LocalDate> dates,
                                          @Query("filter-times[]") List<TourQuery.Time> times,
                                          @Query("filter-neighbourhoods[]") List<String> neighbourhoods,
                                          @Query("filter-price-low") Float minPrice,
                                          @Query("filter-price-high") Float maxPrice
    );

    @GET
    Call<TourListResponse> getToursPage(@Url String pageUrl);
}
