package models.quotes;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by unexpected_err on 19/02/2017.
 */

@IgnoreExtraProperties
public class Comment {

    private String username;
    private String text;
    private String date;
    private String avatar;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
