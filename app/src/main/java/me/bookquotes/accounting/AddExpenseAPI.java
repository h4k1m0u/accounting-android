package me.bookquotes.accounting;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Retrofit API interface (to post expense).
 */

public interface AddExpenseAPI {
    @FormUrlEncoded
    @POST("expenses/")
    Call<Expense> addExpense(@Header("Authorization") String token, @Field("description") String description,
                           @Field("amount") Float amount);
}
