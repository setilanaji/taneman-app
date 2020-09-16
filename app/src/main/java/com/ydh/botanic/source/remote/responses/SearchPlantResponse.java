package com.ydh.botanic.source.remote.responses;

import com.ydh.botanic.models.Plant;

import java.util.List;

public class SearchPlantResponse {

    private final Integer page;
    private final Integer totalPlants;
    private final Integer totalPages;

    private final List<Plant> results;

    public SearchPlantResponse(Integer page, Integer totalPlants, Integer totalPages, List<Plant> results) {
        this.page = page;
        this.totalPlants = totalPlants;
        this.totalPages = totalPages;
        this.results = results;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getTotalPlants() {
        return totalPlants;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public List<Plant> getResults() {
        return results;
    }
}
