package me.bookquotes.expenses;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Retrofit API interface (to get token to authenticate user).
 */

public interface TokenAPI {
    @FormUrlEncoded
    @POST("token/")
    Call<Token> getToken(@Field("username") String username, @Field("password") String password);
}
