package me.bookquotes.accounting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by h4k1m on 28/08/2017.
 */

public class ExpensesFragment extends Fragment {
    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.expenses_fragment, container, false);

        // recyclerview
        mRecyclerView = (RecyclerView) view.findViewById(R.id.expenses);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // get token from extras
        Intent intent = getActivity().getIntent();
        String t = intent.getExtras().getString("token");
        String header = "Token " + t;

        // get expenses
        Retrofit retrofit = Util.getBuilder();
        ExpensesAPI api = retrofit.create(ExpensesAPI.class);
        Call<List<Expense>> call = api.getExpenses(header, Util.getCurrentMonth());
        call.enqueue(new Callback<List<Expense>>() {
            @Override
            public void onResponse(Call<List<Expense>> call, Response<List<Expense>> response) {
                if (response.code() == 200) {
                    // fill recyclerview from feed
                    List<Expense> expenses = response.body();
                    ExpenseAdapter adapter = new ExpenseAdapter(expenses);
                    mRecyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Expense>> call, Throwable t) {
                String message = t.getMessage();
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }
}
