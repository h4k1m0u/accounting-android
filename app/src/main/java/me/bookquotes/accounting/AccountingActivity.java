package me.bookquotes.accounting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
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

public class AccountingActivity extends AppCompatActivity {
    private EditText inputUsername;
    private EditText inputPassword;
    private Button btnLogin;
    private SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // get token from extras
        Intent intent = getIntent();
        String t = intent.getExtras().getString("token");

        // set layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accounting_activity);

        // logging interceptor to print requests (change to BODY for more detail)
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        // initialize the json parser with retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://accounting.bookquotes.me/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        ExpensesAPI api = retrofit.create(ExpensesAPI.class);

        // get expenses
        Call<List<Expense>> call = api.getExpenses("Token " + t);
        call.enqueue(new Callback<List<Expense>>() {
            @Override
            public void onResponse(Call<List<Expense>> call, Response<List<Expense>> response) {
                if (response.code() == 200) {
                    List<Expense> expenses = response.body();
                    int i = response.code();
                } else {
                    Toast.makeText(AccountingActivity.this, response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Expense>> call, Throwable t) {
                String message = t.getMessage();
                Toast.makeText(AccountingActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
