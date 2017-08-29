package me.bookquotes.accounting;

import com.google.gson.annotations.SerializedName;

/**
 * Expense model class.
 */

public class Expense {
    @SerializedName("date")
    private String date;

    @SerializedName("description")
    private String description;

    @SerializedName("amount")
    private float amount;

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public float getAmount() {
        return amount;
    }
}
