package apis;

import models.MessageData;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by madki on 17/06/16.
 */
public interface MessengerApi {

    @POST("message")
    void sendMessage(@Query("access_token") String pageToken, @Body MessageData messageData);
}