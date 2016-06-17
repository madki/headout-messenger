import apis.HeadoutApi;
import apis.MessengerApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import models.*;
import models.headout.City;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ratpack.handling.Chain;
import ratpack.http.Request;
import ratpack.jackson.Jackson;
import ratpack.server.BaseDir;
import ratpack.server.RatpackServer;
import ratpack.groovy.template.TextTemplateModule;
import ratpack.guice.Guice;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import utils.Strings;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import static ratpack.groovy.Groovy.groovyTemplate;

import java.security.cert.CertificateException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private final static Logger LOGGER = LoggerFactory.getLogger(Main.class);

    private static final String TOKEN = "my_awesome_token";
    private static final String PAGE_ACCESS_TOKEN = "EAAQrIZCTlZAS0BABWW4XA5AzAq5fYVxHeZCSK6x0DPljhGoa0z1cZBCBQZAFxfpqISb6JiGbfuTfki5gpRIhHz6FxZB21yz5Uwrtyx566xUUfcHSovR0rygYYUCaa7wPnHW4aJ3CIY2lH8ZB07PrqnGfdJUlU2gP7aRpa5mgmctPQZDZD";
    private static final String MESSAGE_URL = "https://graph.facebook.com/v2.6/me/";

    private static final String HEADOUT_URL = "https://www.test-headout.com/";

    private static final Gson gson = new GsonBuilder().create();

    private static final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

    private static final OkHttpClient messengerClient;
    private static final OkHttpClient headoutClient;


    static {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.networkInterceptors().add(interceptor);

        messengerClient = builder.build();
        prepareHeadoutClient(builder);
        headoutClient = builder.build();
    }

    private static void prepareHeadoutClient(OkHttpClient.Builder client) {
        final TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[]{};
                    }
                }
        };

        try {
            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            client.sslSocketFactory(sslSocketFactory);
            client.hostnameVerifier((hostname, session) -> true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        client.interceptors().add(chain -> {
            okhttp3.Request request = chain.request()
                    .newBuilder()
                    .addHeader("Authorization", Credentials.basic("goku", "kakarot"))
                    .build();
            return chain.proceed(request);
        });
    }

    private static final Retrofit messengerRetrofit = new Retrofit.Builder()
            .client(messengerClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(MESSAGE_URL)
            .build();

    private static final MessengerApi messengerApi = messengerRetrofit.create(MessengerApi.class);


    private static final Retrofit headoutRetrofit = new Retrofit.Builder()
            .client(headoutClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(HEADOUT_URL)
            .build();

    private static final HeadoutApi headoutApi = headoutRetrofit.create(HeadoutApi.class);

    public static void main(String... args) throws Exception {
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        RatpackServer.start(s -> s
                .serverConfig(c -> c
                        .baseDir(BaseDir.find())
                        .env())

                .registry(Guice.registry(b -> b
                        .module(TextTemplateModule.class, conf -> conf.setStaticallyCompile(true))))

                .handlers(chain -> chain
                        .get(ctx -> ctx.render(groovyTemplate("index.html")))

                        .path("webhook", ctx -> ctx.byMethod(m -> m
                                .get(() -> {
                                    Request request = ctx.getRequest();
                                    Map<String, String> queryParams = request.getQueryParams();
                                    if ("subscribe".equals(queryParams.get("hub.mode")) && TOKEN.equals(queryParams.get("hub.verify_token"))) {
                                        ctx.render(queryParams.get("hub.challenge"));
                                    } else {
                                        ctx.render("Hello world!");
                                    }
                                })
                                .post(() -> {
                                    ctx.parse(Jackson.fromJson(WebhookRequest.class)).then(wr -> {
                                        LOGGER.info("request : " + Strings.toString(wr));
                                        System.out.println("request : " + Strings.toString(wr));
                                        processRequest(wr);
                                    });
                                    ctx.getResponse().status(200).send();
                                })
                        ))

                        .files(f -> f.dir("public"))
                )
        );
    }

    private static void processRequest(WebhookRequest webhookRequest) throws Exception {
        if ("page".equals(webhookRequest.getObject())) {
            for (Entry e : webhookRequest.getEntry()) {
                processEntry(e);
            }
        }
    }

    private static void processEntry(Entry entry) throws Exception {
        for (MessageData md : entry.getMessaging()) {
            processMessageData(md);
        }
    }

    private static void processMessageData(MessageData messageData) throws Exception {
        if (messageData.getMessage() != null) {
            processMessage(messageData.getSender(), messageData.getMessage());
        } else if (messageData.getPostback() != null) {
            processPostback(messageData.getSender(), messageData.getPostback());
        } else {
            System.out.println("Unknown messageData, neither message, nor postback");
        }
    }

    private static void processMessage(User user, Message message) throws Exception {
        if (message.getText() != null) {
            MessageData messageData = new MessageData();
            messageData.setRecipient(user);
            Message msg = new Message();
            ButtonsPayload payload = new ButtonsPayload();
            payload.text = "What can I do for you?";
            payload.buttons = new ArrayList<>();
            payload.buttons.add(RedirectButton.create("Take me Headout", "https://www.headout.com"));
            payload.buttons.add(PostbackButton.create("Talk to me", gson.toJson(ShowCitiesPayload.create())));
            msg.setAttachment(Attachment.withButtons(payload, gson));
            messageData.setMessage(msg);
            sendMessage(messageData);
        } else {
            System.out.println("Not a message with text " + message);
        }
    }

    private static void processPostback(User user, Postback postback) throws Exception {
        System.out.println("Postback: " + postback);
        CustomPayloadType type = postback.getPayloadType(gson);
        if (type == CustomPayloadType.SHOW_CITIES) {
            Response<List<City>> citiesResponse = headoutApi.getCities().execute();
            if (citiesResponse.isSuccessful()) {
                List<City> cities = citiesResponse.body();
                GenericPayload genericPayload = new GenericPayload();
                List<StructuredElement> elements = new ArrayList<>(cities.size());
                elements.addAll(cities.stream().map(c -> StructuredElement.fromCity(c, gson)).collect(Collectors.toList()));
                genericPayload.elements = elements;
                MessageData messageData = new MessageData();
                Message msg = new Message();
                msg.setAttachment(Attachment.withElements(genericPayload, gson));
                messageData.setRecipient(user);
                messageData.setMessage(msg);
                sendMessage(messageData);
            }
        } else if (type == CustomPayloadType.SELECT_CITY) {
            Message msg = new Message();
            msg.setText("You have chosen well my friend.");
            MessageData messageData = new MessageData();
            messageData.setRecipient(user);
            messageData.setMessage(msg);
            sendMessage(messageData);
        }
    }

    private static void sendMessage(MessageData messageData) throws Exception {
        Response<JsonElement> result = messengerApi.sendMessage(PAGE_ACCESS_TOKEN, messageData).execute();
        if (result.isSuccessful()) {
            System.out.println("Success: " + result.body().toString());
        } else {
            System.out.println("Error: " + result.errorBody().string());
        }
    }
}
