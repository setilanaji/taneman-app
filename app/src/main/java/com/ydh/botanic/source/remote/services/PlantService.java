package com.ydh.botanic.source.remote.services;

//import com.ydh.botanic.models.Botanic;

import com.ydh.botanic.models.Category;
import com.ydh.botanic.models.Plant;
import com.ydh.botanic.source.remote.responses.CategoryResponse;
import com.ydh.botanic.source.remote.responses.FavoriteResponse;
import com.ydh.botanic.source.remote.responses.PlantResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

//import com.ydh.botanic.source.remote.responses.BotanicResponse;

public interface PlantService {

    @GET("species/")
    Call<PlantResponse> getPlants(
            @Query("language") String language
    );

    @GET("species/{slug}/")
    Call<Plant> getPlant(
            @Path("slug") String slugText,
            @Query("language") String language
    );

    @POST("species/{slug}/favourite_plant")
    Call<FavoriteResponse> postFavoritePlant(
            @Path("slug") String slugText

    );

    @GET("favourite_plant/")
    Call<PlantResponse> getFavourite(
            @Query("page") String page,
            @Query("language") String language

    );

    @GET("species/")
    Call<PlantResponse> searchPlants(
            @Query("search") String query,
            @Query("page") String page,
            @Query("language") String language
    );

    @GET("species/")
    Call<PlantResponse> getPlantsFilter(
            @Query("search") String query,
            @Query("categories") String category,
            @Query("ordering") String order,
            @Query("page") String page,
            @Query("language") String language

    );

//    @GET("garden/")
//    Call<GardenResponse> getGardens();

//    @GET("garden/{slug}")
//    Call<Garden> getGarden(
//            @Path("slug") String slug
//    );

    @GET("category/")
    Call<CategoryResponse> getCategories(
            @Query("language") String language

    );

    @GET("category/")
    Call<CategoryResponse> searchCategories(
            @Query("page") String page,
            @Query("language") String language
    );

    @GET("category")
    Call<CategoryResponse> getCategoriesFilter(
            @Query("plants") String plant,
            @Query("ordering") String order,
            @Query("page") String page,
            @Query("language") String language
    );

    @GET("category/{slug}")
    Call<Category> getCategory(
            @Path("slug") String slug,
            @Query("language") String language

    );

//    @GET("plant/botanic/list/")
//    Call<BotanicResponse> getBotanics();
//
//    @GET("plant/botanic/detail/{id}/")
//    Call<Botanic> getBotanic(
//            @Path("id") int id
//    );
}
