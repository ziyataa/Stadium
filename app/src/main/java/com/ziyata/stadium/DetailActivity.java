package com.ziyata.stadium;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.imbStadium)
    ImageView imbStadium;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appBar)
    AppBarLayout appBar;
    @BindView(R.id.txtNamaStadium)
    TextView txtNamaStadium;
    @BindView(R.id.txtDeskrisi)
    TextView txtDeskrisi;

    private Bundle bundle;
    private int id;
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;
    private List<StadiumData> teamsDataList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bundle = getIntent().getExtras();
        if (bundle != null) {
            id = Integer.valueOf(bundle.getString(Constant.id));
        }

        teamsDataList = new ArrayList<>();

        getData();
        showProgress();

    }

    private void showProgress() {
        progressDialog = new ProgressDialog(DetailActivity.this);
        progressDialog.setTitle("Harap Tunggu");
        progressDialog.setMessage("Loading...");
        progressDialog.show();
    }

    private void getData() {
        apiInterface = ApiClient.getCliend().create(ApiInterface.class);
        Call<StadiumResponse> call = apiInterface.getDetailResponse(id);
        call.enqueue(new Callback<StadiumResponse>() {
            @Override
            public void onResponse(Call<StadiumResponse> call, Response<StadiumResponse> response) {
                StadiumResponse teamsResponse = response.body();
                teamsDataList = teamsResponse.getTeams();
                progressDialog.dismiss();

                txtNamaStadium.setText(teamsDataList.get(0).getStrStadium());
                txtDeskrisi.setText(teamsDataList.get(0).getStrStadiumDescription());
                Glide.with(DetailActivity.this).load(teamsDataList.get(0).getStrStadiumThumb()).into(imbStadium);

                CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
                collapsingToolbarLayout.setTitle(teamsDataList.get(0).getStrStadium());

                collapsingToolbarLayout.setCollapsedTitleTextColor(
                        ContextCompat.getColor(DetailActivity.this, R.color.white));
                collapsingToolbarLayout.setExpandedTitleColor(
                        ContextCompat.getColor(DetailActivity.this, R.color.white));
            }

            @Override
            public void onFailure(Call<StadiumResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(DetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
