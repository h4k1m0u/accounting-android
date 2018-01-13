package com.gistutorials.accounting;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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
        // set layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accounting_activity);

        // add actionbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate menu
        getMenuInflater().inflate(R.menu.accounting_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_expense:
                // open post expense dialog
                Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.expense_add);
                dialog.show();

                // add expense
                final EditText amountEditText = (EditText) dialog.findViewById(R.id.add_expense_amount);
                final EditText descriptionEditText = (EditText) dialog.findViewById(R.id.add_expense_description);
                Button addButton = (Button) dialog.findViewById(R.id.add);
                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // post expense to server
                        final String description = descriptionEditText.getText().toString();
                        final Float amount = Float.valueOf(amountEditText.getText().toString());

                        // get token from extras
                        Intent intent = getIntent();
                        String t = intent.getExtras().getString("token");
                        String header = "Token " + t;

                        // get expenses
                        Retrofit retrofit = Util.getBuilder();
                        AddExpenseAPI api = retrofit.create(AddExpenseAPI.class);
                        Call<Expense> call = api.addExpense(header, description, amount);
                        call.enqueue(new Callback<Expense>() {
                            @Override
                            public void onResponse(Call<Expense> call, Response<Expense> response) {
                                if (response.code() == 201) {
                                    Expense expense = response.body();
                                    String message = String.format("Expense: '%s' was added successfully", expense.getDescription());
                                    Toast.makeText(AccountingActivity.this, message, Toast.LENGTH_LONG).show();

                                    // refresh activity
                                    finish();
                                    startActivity(getIntent());
                                } else {
                                    Toast.makeText(AccountingActivity.this, response.message(), Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Expense> call, Throwable t) {
                                String message = t.getMessage();
                                Toast.makeText(AccountingActivity.this, message, Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
                break;
        }

        return true;
    }
}
