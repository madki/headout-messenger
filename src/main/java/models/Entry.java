package models;

import utils.Strings;

import java.util.List;

/**
 * Created by madki on 17/06/16.
 */
public class Entry {

    private String id;
    private long time;
    private List<MessageData> messaging;

    public void setId(String id) {
        this.id = id;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setMessaging(List<MessageData> messaging) {
        this.messaging = messaging;
    }

    public String getId() {
        return id;
    }

    public long getTime() {
        return time;
    }

    public List<MessageData> getMessaging() {
        return messaging;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "id='" + id + '\'' +
                ", time=" + time +
                ", messaging=" + Strings.toString(messaging) +
                '}';
    }
}
