package models;

import com.google.gson.JsonElement;
import models.Button;

/**
 * Created by madki on 17/06/16.
 */
public class PostbackButton extends Button {
    private final String payload;

    private PostbackButton(String title, String payload) {
        super(Button.TYPE_POST_BACK, title);
        this.payload = payload;
    }

    public static PostbackButton create(String title, String payload) {
        return new PostbackButton(title, payload);
    }
}
