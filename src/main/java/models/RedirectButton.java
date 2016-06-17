package models;

/**
 * Created by madki on 17/06/16.
 */
public class RedirectButton extends Button {
    private final String url;

    private RedirectButton(String title, String url) {
        super(Button.TYPE_WEB, title);
        this.url = url;
    }

    public static RedirectButton create(String title, String url) {
        return new RedirectButton(title, url);
    }
}
