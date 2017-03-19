package com.edfora.sunny.edfora;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.edfora.sunny.edfora.adapters.TracksAdapter;
import com.edfora.sunny.edfora.models.Track;
import com.edfora.sunny.edfora.network.EdforaApi;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rv_tracks)
    RecyclerView rvTracks;
    ProgressDialog progressDialog;
    @BindView(R.id.et_search_box)
    EditText etSearchBox;
    private TracksAdapter tracksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading_message));
        ButterKnife.bind(this);
        getTrackList();
        etSearchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tracksAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }


    private void getTrackList() {
        progressDialog.show();
        Call<List<Track>> trackList = EdforaApi.getApi().getTrackDetails();
        trackList.enqueue(new Callback<List<Track>>() {
            @Override
            public void onResponse(Call<List<Track>> call, Response<List<Track>> response) {
                if (response.isSuccessful()) {
                    TrackRepository.getInstance().setTracks(response.body());
                    setAdapter();
                } else {
                    Snackbar.make(rvTracks, R.string.loading_error, Snackbar.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<Track>> call, Throwable t) {
                Snackbar.make(rvTracks, R.string.loading_failed, Snackbar.LENGTH_INDEFINITE).setAction(getString(R.string.retry), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getTrackList();
                    }
                })
                        .setActionTextColor(Color.RED)
                        .show();
                progressDialog.dismiss();
            }
        });
    }


    private void setAdapter() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvTracks.setLayoutManager(mLayoutManager);
        rvTracks.setItemAnimator(new DefaultItemAnimator());
        tracksAdapter = new TracksAdapter(this, TrackRepository.getInstance().getTracks());
        rvTracks.setAdapter(tracksAdapter);
    }


}
