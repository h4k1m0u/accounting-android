package me.bookquotes.expenses;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
    private SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // check if had already authenticated (saved token)
        mPrefs = getSharedPreferences("prefs", 0);
        String t = mPrefs.getString("token", null);
        if (t != null) {
            // Move to another activity
            Toast.makeText(this, "Token exists", Toast.LENGTH_LONG).show();
        }

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
                logging.setLevel(HttpLoggingInterceptor.Level.NONE);
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
                            // save token in the preferences
                            Token token = response.body();
                            String t = token.getToken();
                            SharedPreferences.Editor editor = mPrefs.edit();
                            editor.putString("token", t);
                            editor.commit();

                            Toast.makeText(MainActivity.this, "Token=" + t, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainActivity.this, response.message(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Token> call, Throwable t) {
                        String message = t.getMessage();
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}
