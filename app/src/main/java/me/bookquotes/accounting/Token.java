package me.bookquotes.accounting;

import com.google.gson.annotations.SerializedName;

/**
 * Token model class.
 */

public class Token {
    @SerializedName("token")
    private String token;

    public String getToken() {
        return token;
    }
}
