package com.ydh.botanic.source.remote.responses;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;
import com.ydh.botanic.models.Botanic;
import com.ydh.botanic.models.Category;

import java.util.List;

public class CategoryResponse {

    @SerializedName("count")
    private int count;

    @SerializedName("results")
    private List<Category> results;

    @SerializedName("total_pages")
    private int totalPages;

    @Nullable
    public List<Category> getCategories(){ return results; }

    public int getTotalPages() { return totalPages; }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "Category Response{" +
                "count=" + count +
                ", results=" + results +
                ", total pages=" + totalPages +
                '}';
    }
}
