package com.ydh.botanic.source.remote.responses;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;
import com.ydh.botanic.models.Plant;

import java.util.List;

public class PlantResponse {

    @SerializedName("count")
    private int count;

    @SerializedName("results")
    private List<Plant> results;

    @SerializedName("total_pages")
    private int totalPages;

    @Nullable
    public List<Plant> getPlants(){ return results; }

    public int getCount() {
        return count;
    }

    public int getTotalPages() {
        return totalPages;
    }

    @Override
    public String toString() {
        return "PlantResponces{" +
                "count=" + count +
                ", results=" + results +
                ", total pages=" + totalPages +
                '}';
    }
}
