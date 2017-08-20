package me.bookquotes.expenses;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Basic authentication with retrofit:
 * https://futurestud.io/tutorials/android-basic-authentication-with-retrofit
 * https://stackoverflow.com/q/45190997/2228912
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // set layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize http client with token
        String token = Credentials.basic("hakim", "L57f841C");
        AuthenticationInterceptor interceptor = new AuthenticationInterceptor(token);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(interceptor);

        // initialize the json parser with retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://accounting.bookquotes.me")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        ExpensesAPI api = retrofit.create(ExpensesAPI.class);

        // basic authentication
        Call<List<Expense>> call = api.getExpenses();
        call.enqueue(new Callback<List<Expense>>() {
            @Override
            public void onResponse(Call<List<Expense>> call, Response<List<Expense>> response) {
                // TODO: show a toast for unauthorized (change token to test)
                Log.d("ExpensesResponse", response.message());
            }

            @Override
            public void onFailure(Call<List<Expense>> call, Throwable t) {
                String message = t.getMessage();
                Log.d("ExpensesError", message);
            }
        });
    }
}
