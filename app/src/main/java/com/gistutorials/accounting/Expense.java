package com.gistutorials.accounting;

import com.google.gson.annotations.SerializedName;

/**
 * Expense model class.
 */

public class Expense {
    @SerializedName("date")
    private String date;

    @SerializedName("description")
    private String description;

    @SerializedName("user")
    private String user;

    @SerializedName("amount")
    private float amount;

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getUser() {
        return user;
    }

    public float getAmount() {
        return amount;
    }
}
