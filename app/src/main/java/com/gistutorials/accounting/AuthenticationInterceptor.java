package com.gistutorials.accounting;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by h4k1m on 20/08/2017.
 */

public class AuthenticationInterceptor implements Interceptor {
    private String token;

    public AuthenticationInterceptor(String token) {
        this.token = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        // add authorization header to intercepted request
        Request request = chain
                .request()
                .newBuilder()
                .addHeader("Authorization", this.token)
                .build();

        return chain.proceed(request);
    }
}
