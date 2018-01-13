package com.gistutorials.accounting;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Retrofit API interface to get the connected user.
 */

public interface UserAPI {
    @GET("auth/me/")
    Call<User> getUser(@Header("Authorization") String token);
}
