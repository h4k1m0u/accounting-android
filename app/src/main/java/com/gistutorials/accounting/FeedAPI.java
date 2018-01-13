package com.gistutorials.accounting;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Retrofit API interface (to get feed of expenses only by authenticated users).
 */

public interface FeedAPI {
    @GET("expenses/?format=json")
    Call<Feed> getFeed(@Header("Authorization") String token, @Query("month") int month);
}
