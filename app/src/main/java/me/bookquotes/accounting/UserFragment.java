package me.bookquotes.accounting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by h4k1m on 28/08/2017.
 */

public class UserFragment extends Fragment {
    private TextView mUserTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_fragment, container, false);
        mUserTextView = (TextView) view.findViewById(R.id.user);

        // get token from extras
        Intent intent = getActivity().getIntent();
        String t = intent.getExtras().getString("token");
        String header = "Token " + t;

        // get user
        Retrofit retrofit = Util.getBuilder();
        UserAPI api = retrofit.create(UserAPI.class);
        Call<User> call = api.getUser(header);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    // fill textview from feed
                    User user = response.body();
                    mUserTextView.setText(user.getUsername());
                } else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                String message = t.getMessage();
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }
}
