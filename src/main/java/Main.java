import apis.HeadoutApi;
import apis.MessengerApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.*;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ratpack.http.Request;
import ratpack.jackson.Jackson;
import ratpack.server.BaseDir;
import ratpack.server.RatpackServer;
import ratpack.groovy.template.TextTemplateModule;
import ratpack.guice.Guice;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static ratpack.groovy.Groovy.groovyTemplate;

import java.util.*;

public class Main {
    private final static Logger LOGGER = LoggerFactory.getLogger(Main.class);

    private static final String TOKEN = "my_awesome_token";
    private static final String PAGE_ACCESS_TOKEN = "EAAQrIZCTlZAS0BABWW4XA5AzAq5fYVxHeZCSK6x0DPljhGoa0z1cZBCBQZAFxfpqISb6JiGbfuTfki5gpRIhHz6FxZB21yz5Uwrtyx566xUUfcHSovR0rygYYUCaa7wPnHW4aJ3CIY2lH8ZB07PrqnGfdJUlU2gP7aRpa5mgmctPQZDZD";
    private static final String MESSAGE_URL = "https://graph.facebook.com/v2.6/me/";

    private static final Gson gson = new GsonBuilder().create();
    private static final OkHttpClient messengerClient = new OkHttpClient.Builder().build();

    private static final Retrofit messengerRetrofit = new Retrofit.Builder()
            .client(messengerClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(MESSAGE_URL)
            .build();

    private static final MessengerApi messengerApi = messengerRetrofit.create(MessengerApi.class);
    
    private static final OkHttpClient headoutClient = new OkHttpClient.Builder().build();

    private static final Retrofit headoutRetrofit = new Retrofit.Builder()
            .client(headoutClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(MESSAGE_URL)
            .build();

    private static final HeadoutApi headoutApi = headoutRetrofit.create(HeadoutApi.class);

    public static void main(String... args) throws Exception {
        RatpackServer.start(s -> s
                .serverConfig(c -> c
                        .baseDir(BaseDir.find())
                        .env())

                .registry(Guice.registry(b -> b
                        .module(TextTemplateModule.class, conf -> conf.setStaticallyCompile(true))))

                .handlers(chain -> chain
                        .get(ctx -> ctx.render(groovyTemplate("index.html")))

                        .get("webhook", ctx -> {
                            Request request = ctx.getRequest();
                            Map<String, String> queryParams = request.getQueryParams();
                            if ("subscribe".equals(queryParams.get("hub.mode")) && TOKEN.equals(queryParams.get("hub.verify_token"))) {
                                ctx.render(queryParams.get("hub.challenge"));
                            } else {
                                ctx.render("Hello world!");
                            }
                        })

                        .post("webhook", ctx -> {
                            ctx.parse(Jackson.fromJson(WebhookRequest.class)).then(Main::processRequest);
                            ctx.getResponse().status(200).send();
                        })

                        .files(f -> f.dir("public"))
                )
        );
    }

    private static void processRequest(WebhookRequest webhookRequest) {
        if ("page".equals(webhookRequest.getObject())) {
            webhookRequest.getEntry().forEach(Main::processEntry);
        }
    }

    private static void processEntry(Entry entry) {
        entry.getMessaging().forEach(Main::processMessageData);
    }

    private static void processMessageData(MessageData messageData) {
        if (messageData.getMessage() != null) {
            processMessage(messageData.getSender(), messageData.getMessage());
        } else if (messageData.getPostback() != null) {
            processPostback(messageData.getSender(), messageData.getPostback());
        } else {
            LOGGER.warn("Unknown messageData, neither message, nor postback");
        }
    }

    private static void processMessage(User sender, Message message) {
        if (message.getText() != null) {
            MessageData messageData = new MessageData();
            messageData.setSender(sender);
            messageData.setMessage(message);
            messengerApi.sendMessage(PAGE_ACCESS_TOKEN, messageData);
        } else {
            LOGGER.info("Not a message with text " + message);
        }
    }

    private static void processPostback(User sender, Postback postback) {
        LOGGER.info("Postback: " + postback);
    }
}
