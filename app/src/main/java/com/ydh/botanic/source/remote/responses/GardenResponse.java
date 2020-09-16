package com.ydh.botanic.source.remote.responses;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;
import com.ydh.botanic.models.Garden;

import java.util.List;

public class GardenResponse {

    @SerializedName("count")
    private int count;

    @SerializedName("results")
    private List<Garden> results;

    @SerializedName("total_pages")
    private int totalPages;

    @Nullable
    public List<Garden> getGardens(){ return results; }

    public int getCount() {
        return count;
    }

    public int getTotalPages() { return totalPages; }

    @Override
    public String toString() {
        return "Garden Response{" +
                "count=" + count +
                ", results=" + results +
                ", total pages=" + totalPages +
                '}';
    }
}
