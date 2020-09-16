package com.ydh.botanic.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.ydh.botanic.models.Plant;
import com.ydh.botanic.source.remote.responses.FavoriteResponse;
import com.ydh.botanic.source.remote.responses.PlantResponse;
import com.ydh.botanic.source.remote.services.PlantService;
import com.ydh.botanic.utils.ApiUtil;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteViewModel extends BaseViewModel{

    private static final PlantService plantService = ApiUtil.getPlantService();

    private final MutableLiveData<ArrayList<Plant>> listFavourite = new MutableLiveData<>();

    public MutableLiveData<ArrayList<Plant>> getListFavourite() {
        return listFavourite;
    }

    public FavoriteViewModel(@NonNull Application application) {
        super(application);
    }

    private MutableLiveData<Boolean> isQueryExhausted = new MutableLiveData<>();

    private MutableLiveData<Boolean> isFavSucced = new MutableLiveData<>();

    public MutableLiveData<Boolean> getIsQueryExhausted() {
        return isQueryExhausted;
    }



    private int pageNumber = 1;
    private boolean isPerformingQuery;
    private boolean isFavPerformingQuery;
    private boolean isExhausted;

    public void setListFavourite( final int pageNumber,final String language) {
        isPerformingQuery = true;
        plantService.getFavourite(String.valueOf(pageNumber), language).enqueue(new Callback<PlantResponse>() {
            @Override
            public void onResponse(Call<PlantResponse> call, Response<PlantResponse> response) {
                if (response.isSuccessful()) {
                    ArrayList<Plant> favourite = new ArrayList<>(Objects.requireNonNull(response.body()).getPlants());
                    isPerformingQuery = false;
                    isQueryExhausted.postValue(false);
                    isExhausted = false;
                    listFavourite.postValue(favourite);
                    Log.d("favourite", "success loading from API");

                } else {
                    int statusCode = response.code();
                    isQueryExhausted.postValue(true);
                    isExhausted = true;
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<PlantResponse> call, Throwable t) {
                String b = t.toString();
                Log.d("favourite", "error loading from API" + b);

            }
        });

    }

    public void setFavorite(final String slug) {
//        isFavPerformingQuery.postValue(true);
        isFavPerformingQuery = true;
        System.out.println("FavVM "+ slug);
        plantService.postFavoritePlant(slug).enqueue(new Callback<FavoriteResponse>() {
            @Override
            public void onResponse(Call<FavoriteResponse> call, Response<FavoriteResponse> response) {
                if (response.isSuccessful()) {
//                    ArrayList<Plant> favourite = new ArrayList<>(Objects.requireNonNull(response.body()).getPlants());
                    isFavPerformingQuery = false;
                    isFavSucced.postValue(true);
//                    isExhausted = false;
//                    listFavourite.postValue(favourite);
                    Log.d("favourite", "success loading from API");

                } else {
                    int statusCode = response.code();
                    isFavSucced.postValue(false);
                    isFavPerformingQuery = false;
//                    isQueryExhausted.postValue(true);
//                    isExhausted = true;
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<FavoriteResponse> call, Throwable t) {
                String b = t.toString();
                Log.d("favourite", "error loading from API" + b);

            }
        });

    }



    public void searchFavouriteNextPage(final String language){
        Log.d("searchFavNextPage", "scroll test is worked");
        if(!isExhausted && !isPerformingQuery){
            pageNumber++;
            setListFavourite( pageNumber, language);

        }else {
            isQueryExhausted.postValue(true);
        }

    }

    public void executeFavorite(final String slug){
        Log.d("executeFavorite", "click test is worked");
        if(!isFavPerformingQuery){
//            pageNumber++;
            setFavorite(  slug);

        }else {
            isQueryExhausted.postValue(true);
        }

    }
}
