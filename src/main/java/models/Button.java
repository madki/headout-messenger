package models;

import com.google.gson.JsonElement;

/**
 * Created by madki on 17/06/16.
 */
public class Button {
    public static final String TYPE_WEB = "web_url";
    public static final String TYPE_POST_BACK = "postback";

    private final String type;
    private final String title;

    Button(String type, String title) {
        this.type = type;
        this.title = title;
    }
}
