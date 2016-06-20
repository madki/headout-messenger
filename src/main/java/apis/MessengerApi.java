package apis;

import com.google.gson.JsonElement;
import models.FirstMessage;
import models.MessageData;
import ratpack.handling.UserId;
import retrofit2.Call;
import retrofit2.http.*;

/**
 * Created by madki on 17/06/16.
 */
public interface MessengerApi {

    @POST("me/messages")
    Call<JsonElement> sendMessage(@Query("access_token") String pageToken, @Body MessageData messageData);

    @POST("{pageId}/thread_settings")
    Call<JsonElement> sendInitialMessage(@Path("pageId") String pageId, @Query("access_token") String pageToken, @Body FirstMessage message);

    @GET("{userId}")
    Call<JsonElement> getUserDetails(@Path("userId") String userId, @Query("access_token") String pageToken, @Query("fields") String fields);


}
