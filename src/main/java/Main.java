import apis.HeadoutApi;
import apis.MessengerApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import models.*;
import models.headout.*;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ratpack.handling.Context;
import ratpack.http.Request;
import ratpack.jackson.Jackson;
import ratpack.server.BaseDir;
import ratpack.server.RatpackServer;
import ratpack.groovy.template.TextTemplateModule;
import ratpack.guice.Guice;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import utils.Strings;
import utils.TextUtils;

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
    private static final String MESSAGE_URL = "https://graph.facebook.com/v2.6/";

    private static final String HEADOUT_URL = "https://www.test-headout.com/";
    private static final String PAGE_ID = "1032573553458368";

    private static final String FIELDS = "first_name,last_name,profile_pic,locale,timezone,gender";

    private static final Gson gson = new GsonBuilder().create();

    private static final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

    private static final OkHttpClient messengerClient;
    private static final OkHttpClient headoutClient;

    private static List<City> cities;


    static {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.networkInterceptors().add(interceptor);

        messengerClient = builder.build();
        prepareHeadoutClient(builder);
        headoutClient = builder.build();

        try {
            fetchCities();
        } catch (Exception e) {
            System.out.println("Unable to fetch cities");
        }
    }

    private static void prepareHeadoutClient(OkHttpClient.Builder client) {
        final X509TrustManager tm = new X509TrustManager() {
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
        };
        final TrustManager[] trustAllCerts = new TrustManager[]{
                tm
        };

        try {
            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            client.sslSocketFactory(sslSocketFactory, tm);
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

                        .get("init", Main::setFirstMessage)

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
                                    System.out.println("Post received");
                                    ctx.parse(Jackson.jsonNode()).then(
                                            jn -> {
                                                System.out.println("Json: " + jn);
                                                processRequest(gson.fromJson(jn.toString(), WebhookRequest.class));
                                            }
                                    );
                                    ctx.getResponse().status(200).send();
                                })
                        ))

                        .files(f -> f.dir("public"))
                )
        );
    }

    private static void setFirstMessage(Context ctx) throws Exception {
        fetchCities();
        FirstMessage firstMessage = FirstMessage.create(
                Message.create(Attachment.withElements(citiesPayload(), gson))
        );
        System.out.println("First message: " + firstMessage);
        messengerApi.sendInitialMessage(PAGE_ID, PAGE_ACCESS_TOKEN, firstMessage).execute();
        ctx.getResponse().status(200).send();
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
            ButtonsPayload payload = ButtonsPayload.create(
                    "What can I do for you?",
                    RedirectButton.create("Take me Headout", "https://www.headout.com"),
                    PostbackButton.create("Talk to me", gson.toJson(ShowCitiesPayload.create())
                    ));
            sendMessage(MessageData.withAttachment(user, Attachment.withButtons(payload, gson)));
        } else {
            System.out.println("Not a message with text " + message);
        }
    }

    private static GenericPayload citiesPayload() {
        return GenericPayload.create(
                cities.stream()
                        .map(c -> StructuredElement.fromCity(c, gson))
                        .toArray(StructuredElement[]::new)
        );
    }

    private static void processPostback(User user, Postback postback) throws Exception {
        System.out.println("Postback: " + postback);
        CustomPayloadType type = postback.getPayloadType(gson);
        if (type == CustomPayloadType.SHOW_CITIES) {
            fetchCities();
            sendMessage(MessageData.withAttachment(user, Attachment.withElements(citiesPayload(), gson)));
        } else if (type == CustomPayloadType.SELECT_CITY) {
            City selectedCity = postback.selectCityPayload(gson).getCity();
            sendMessage(MessageData.withMessage(user, "What would you like to see in " + selectedCity.displayName + "?"));
            List<Category> categories = fetch(headoutApi.getCategories(selectedCity.cityCode));
            sendMessage(MessageData.withAttachment(
                    user,
                    Attachment.withElements(
                            GenericPayload.create(
                                    categories.stream()
                                            .limit(9)
                                            .map(c -> StructuredElement.fromCategory(c, selectedCity, gson))
                                            .collect(Collectors.toList())
                            ),
                            gson))
            );
        } else if (type == CustomPayloadType.SELECT_CATEGORY) {
            SelectCategoryPayload selectCategoryPayload = postback.selectCategoryPayload(gson);
            sendMessage(MessageData.withMessage(user, "Here's Headout top 10 from " + selectCategoryPayload.getCategory().displayName + " collection in " + selectCategoryPayload.getCity().displayName));
            TourQuery query = TourQuery.builder()
                    .categoryId(selectCategoryPayload.getCategory().id)
                    .tags(selectCategoryPayload.getCategory().tags)
                    .build();
            TourListResponse response = fetch(query.fetch(headoutApi));
            List<StructuredElement> elements = new ArrayList<>(response.items.size() + 1);
            elements.addAll(response.items.stream().limit(9).map(t -> StructuredElement.fromTour(t, response.metaData.currency(), gson)).collect(Collectors.toList()));
            if (!TextUtils.isEmpty(response.pageInfo.nextPageUrl)) {
                StructuredElement se = new StructuredElement();
                se.buttons = Arrays.asList(
                        RedirectButton.create("Website", HeadoutApi.WEBSITE_BASE_UTL + "/tours/" + Strings.toUrlParam(response.metaData.city.code)),
                        PostbackButton.create("More", gson.toJson(PaginateToursPayload.create(response.pageInfo.nextPageUrl)))
                );
                elements.add(se);
            }
            sendMessage(MessageData.withAttachment(user, Attachment.withElements(GenericPayload.create(elements), gson)));
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

    private static <T> T fetch(Call<T> call) throws Exception {
        Response<T> response = call.execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            throw new Exception("Unable to fetch results");
        }
    }

    private static void fetchCities() throws Exception {
        if (cities == null) {
            cities = fetch(headoutApi.getCities());
        }
    }

}


