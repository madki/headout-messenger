package apis;

import models.headout.City;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

/**
 * Created by madki on 17/06/16.
 */
public interface HeadoutApi {
    @GET("api/v2/city/list")
    Call<List<City>> getCities();
}
