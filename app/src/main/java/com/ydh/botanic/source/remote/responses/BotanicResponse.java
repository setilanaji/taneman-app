package com.ydh.botanic.source.remote.responses;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;
import com.ydh.botanic.models.Botanic;
import com.ydh.botanic.models.Garden;

import java.util.List;

public class BotanicResponse {
    @SerializedName("count")
    private int count;

    @SerializedName("results")
    private List<Botanic> results;

    @SerializedName("total_pages")
    private int totalPages;

    @Nullable
    public List<Botanic> getBotanics(){ return results; }

    public int getTotalPages() { return totalPages; }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "Botanic Response{" +
                "count=" + count +
                ", results=" + results +
                ", total pages=" + totalPages +
                '}';
    }
}
