package com.ydh.botanic.source.remote.services;

import com.ydh.botanic.models.Taxonomy;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TaxonomyService {
    @GET("species/match/")
    Call<Taxonomy> getTaxon(
            @Query("name") String name
    );
}
