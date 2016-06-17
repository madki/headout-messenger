package models;

import utils.Strings;

/**
 * Created by madki on 17/06/16.
 */
public class Attachment {
    private String type;
    private Payload payload;

    public void setType(String type) {
        this.type = type;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public String getType() {
        return type;
    }

    public Payload getPayload() {
        return payload;
    }

    public static class Payload {
        private String url;

        Payload(String url) {
            this.url = url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "type='" + type + '\'' +
                ", payload=" + Strings.toString(payload) +
                '}';
    }
}
