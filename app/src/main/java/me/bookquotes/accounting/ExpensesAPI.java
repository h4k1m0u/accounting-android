package me.bookquotes.accounting;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Retrofit API interface (to get expenses only by authenticated users).
 */

public interface ExpensesAPI {
    @GET("api/expenses/?format=json")
    Call<List<Expense>> getExpenses(@Header("Authorization") String token);
}
