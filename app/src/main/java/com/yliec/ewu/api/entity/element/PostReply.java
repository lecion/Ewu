package com.yliec.ewu.api.entity.element;

/**
 * Created by Lecion on 11/30/15.
 */
public class PostReply {
    private String content;

    private String reply_id;

    @Override
    public String toString() {
        return "PostReply{" +
                "content='" + content + '\'' +
                ", reply_id='" + reply_id + '\'' +
                '}';
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReply_id() {
        return reply_id;
    }

    public void setReply_id(String reply_id) {
        this.reply_id = reply_id;
    }
}
