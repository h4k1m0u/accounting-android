package com.gistutorials.accounting;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Retrofit API interface to login user and get a token.
 */

public interface LoginAPI {
    @FormUrlEncoded
    @POST("auth/token/create/")
    Call<Token> login(@Field("username") String username, @Field("password") String password);
}
