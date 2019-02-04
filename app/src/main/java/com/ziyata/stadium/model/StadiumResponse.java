package com.ziyata.stadium.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StadiumResponse {

    @SerializedName("teams")
    private List<StadiumData> teams;

    public List<StadiumData> getTeams() {
        return teams;
    }

    public void setTeams(List<StadiumData> teams) {
        this.teams = teams;
    }
}
