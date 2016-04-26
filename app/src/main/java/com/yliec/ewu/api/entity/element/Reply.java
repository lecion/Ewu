package com.yliec.ewu.api.entity.element;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Lecion on 4/13/16.
 */
public class Reply {
    @SerializedName("_id")
    String id;

    @SerializedName("from")
    User user;

    @SerializedName("to")
    User toUser;

    @SerializedName("content")
    String content;

    @SerializedName("update_at")
    String time;


    @SerializedName("reply_id")
    String replyId;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getReplyId() {
        return replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Reply{" +
                "id='" + id + '\'' +
                ", user=" + user +
                ", toUser=" + toUser +
                ", content='" + content + '\'' +
                ", time='" + time + '\'' +
                ", replyId='" + replyId + '\'' +
                '}';
    }
}
