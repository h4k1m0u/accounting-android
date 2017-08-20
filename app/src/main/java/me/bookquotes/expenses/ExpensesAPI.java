package me.bookquotes.expenses;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Retrofit API interface (to get expenses only by authenticated users).
 */

public interface ExpensesAPI {
    @GET("api/expenses/?format=json")
    Call<List<Expense>> getExpenses();
}
