package com.gistutorials.accounting;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Retrofit API interface to get total of expenses of the connected users.
 */

public interface TotalAPI {
    @GET("expenses/total/")
    Call<Float> getTotal(@Header("Authorization") String token, @Query("month") int month);
}
