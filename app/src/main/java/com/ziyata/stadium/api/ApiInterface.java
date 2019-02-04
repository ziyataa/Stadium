package com.ziyata.stadium.api;

import com.ziyata.stadium.model.StadiumResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("/api/v1/json/1/search_all_teams.php")
    Call<StadiumResponse> getTeamResponse(
            @Query("l") String l
    );

    @GET("/api/v1/json/1/lookupteam.php")
    Call<StadiumResponse> getDetailResponse(
            @Query("id") int id
    );
}
