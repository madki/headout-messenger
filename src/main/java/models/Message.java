package models;

import utils.Strings;

import java.util.List;

/**
 * Created by madki on 17/06/16.
 */
public class Message {

    private String mid;
    private int seq;
    private String text;
    private List<Attachment> attachments;

    public void setMid(String mid) {
        this.mid = mid;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public String getMid() {
        return mid;
    }

    public int getSeq() {
        return seq;
    }

    public String getText() {
        return text;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    @Override
    public String toString() {
        return "Message{" +
                "mid='" + mid + '\'' +
                ", seq=" + seq +
                ", text='" + text + '\'' +
                ", attachments=" + Strings.toString(attachments) +
                '}';
    }
}
