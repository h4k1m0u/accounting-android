package com.gistutorials.accounting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by h4k1m on 28/08/2017.
 */

public class TotalFragment extends Fragment {
    private TextView mTotalTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.total_fragment, container, false);
        mTotalTextView = (TextView) view.findViewById(R.id.expenses_total);

        // get token from extras
        Intent intent = getActivity().getIntent();
        String t = intent.getExtras().getString("token");
        String header = "Token " + t;

        // get total
        Retrofit retrofit = Util.getBuilder();
        TotalAPI api = retrofit.create(TotalAPI.class);
        Call<Float> call = api.getTotal(header, Util.getCurrentMonth());
        call.enqueue(new Callback<Float>() {
            @Override
            public void onResponse(Call<Float> call, Response<Float> response) {
                if (response.code() == 200) {
                    // fill textview from feed
                    Float total = response.body();
                    NumberFormat format = NumberFormat.getCurrencyInstance(Locale.UK);
                    mTotalTextView.setText(format.format(total));
                } else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Float> call, Throwable t) {
                String message = t.getMessage();
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }
}
