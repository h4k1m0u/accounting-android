package me.bookquotes.expenses;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

public class MainActivity extends AppCompatActivity {
    private EditText inputUsername;
    private EditText inputPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // set layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // button click listener
        inputUsername = (EditText) findViewById(R.id.username);
        inputPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // get username and password
                String username = inputUsername.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

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
                TokenAPI api = retrofit.create(TokenAPI.class);

                // token authentication
                Call<Token> call = api.getToken(username, password);
                call.enqueue(new Callback<Token>() {
                    @Override
                    public void onResponse(Call<Token> call, Response<Token> response) {
                        if (response.code() == 200) {
                            // get token
                            Token token = response.body();
                            Log.d("Success", token.getToken());
                        } else {
                            Log.d("Error", response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<Token> call, Throwable t) {
                        String message = t.getMessage();
                        Log.d("Error", message);
                    }
                });
            }
        });
    }
}
