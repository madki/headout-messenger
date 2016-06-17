package models;

import utils.Strings;

import java.util.List;

/**
 * Created by madki on 17/06/16.
 */
public class WebhookRequest {
    private String object;
    private List<Entry> entry;

    public void setObject(String object) {
        this.object = object;
    }

    public void setEntry(List<Entry> entry) {
        this.entry = entry;
    }

    public String getObject() {
        return object;
    }

    public List<Entry> getEntry() {
        return entry;
    }

    @Override
    public String toString() {
        return "WebhookRequest{" +
                "object='" + object + '\'' +
                ", entry=" + Strings.toString(entry) +
                '}';
    }
}
