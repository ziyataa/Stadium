package com.ziyata.stadium;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.ziyata.stadium.adapter.AdapterStadium;
import com.ziyata.stadium.api.ApiClient;
import com.ziyata.stadium.api.ApiInterface;
import com.ziyata.stadium.model.Constant;
import com.ziyata.stadium.model.StadiumData;
import com.ziyata.stadium.model.StadiumResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rvStadium)
    RecyclerView rvStadium;
    @BindView(R.id.swiperefresh)

    SwipeRefreshLayout swiperefresh;
    private List<StadiumData> dataResponseList;
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        dataResponseList = new ArrayList<>();

        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }

        });

        getData();
        
        showProgress();
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

    }

    private void showProgress() {
    }

    private void getData() {
        apiInterface = ApiClient.getCliend().create(ApiInterface.class);
        retrofit2.Call<StadiumResponse> call = apiInterface.getTeamResponse(Constant.l);
        call.enqueue(new Callback<StadiumResponse>() {
            @Override
            public void onResponse(Call<StadiumResponse> call, Response<StadiumResponse> response) {
                progressDialog.dismiss();
                swiperefresh.setRefreshing(false);
                StadiumResponse teamsResponse = response.body();
                dataResponseList = teamsResponse.getTeams();

                rvStadium.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                rvStadium.setAdapter(new AdapterStadium(MainActivity.this, dataResponseList));
            }

            @Override
            public void onFailure(Call<StadiumResponse> call, Throwable t) {
                progressDialog.dismiss();
                swiperefresh.setRefreshing(false);
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}