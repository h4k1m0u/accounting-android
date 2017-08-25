package me.bookquotes.expenses;

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
    private String amount;

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getAmount() {
        return amount;
    }
}
