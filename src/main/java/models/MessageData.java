package models;

import utils.Strings;

/**
 * Created by madki on 17/06/16.
 */
public class MessageData {

    private User sender;
    private User recipient;
    private long timestamp;
    private Message message;
    private Postback postback;

    public static MessageData withAttachment(User recipient, Attachment attachment) {
        MessageData md = new MessageData();
        md.setRecipient(recipient);
        md.setMessage(Message.create(attachment));
        return md;
    }

    public static MessageData withMessage(User recipient, String text) {
        MessageData md = new MessageData();
        md.setRecipient(recipient);
        md.setMessage(Message.create(text));
        return md;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public void setPostback(Postback postback) {
        this.postback = postback;
    }

    public User getSender() {
        return sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public Message getMessage() {
        return message;
    }

    public Postback getPostback() {
        return postback;
    }

    @Override
    public String toString() {
        return "MessageData{" +
                "sender=" + sender +
                ", recipient=" + recipient +
                ", timestamp=" + timestamp +
                ", message=" + Strings.toString(message) +
                ", postback=" + Strings.toString(postback) +
                '}';
    }
}
