package me.bookquotes.accounting;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Feed model class (list and number of expenses, and link to previous and next feed pages).
 */

public class Feed {
    @SerializedName("count")
    private int count;

    @SerializedName("next")
    private String next;

    @SerializedName("previous")
    private String previous;

    @SerializedName("results")
    private List<Expense> results;

    public int getCount() {
        return count;
    }

    public String getNext() {
        return next;
    }

    public String getPrevious() {
        return previous;
    }

    public List<Expense> getResults() {
        return results;
    }
}
