package models;

import utils.Strings;

import java.util.List;

/**
 * Created by madki on 17/06/16.
 */
public class Message {

    private String mid;
    private Integer seq;
    private String text;
    private List<Attachment> attachments;
    private Attachment attachment;

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

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    public Message onlyBody() {
        Message m = new Message();
        m.setText(getText());
        m.setAttachments(getAttachments());
        m.setAttachment(getAttachment());
        return m;
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
