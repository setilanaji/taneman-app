package com.ydh.botanic.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ydh.botanic.models.Category;
import com.ydh.botanic.models.Plant;
import com.ydh.botanic.models.Taxonomy;
import com.ydh.botanic.source.remote.responses.CategoryResponse;
import com.ydh.botanic.source.remote.responses.PlantResponse;
import com.ydh.botanic.source.remote.services.PlantService;
import com.ydh.botanic.source.remote.services.TaxonomyService;
import com.ydh.botanic.utils.ApiUtil;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlantViewModel extends BaseViewModel {

    private final MutableLiveData<Plant> plant = new MutableLiveData<>();
    private final MutableLiveData<Category> category = new MutableLiveData<>();
    private final MutableLiveData<Taxonomy> taxonomy = new MutableLiveData<>();

    private static final PlantService plantService = ApiUtil.getPlantService();
    private static final TaxonomyService taxonomyService = ApiUtil.getTaxonomyService();

    private final MutableLiveData<ArrayList<Plant>> listPlants = new MutableLiveData<>();

    private final MutableLiveData<ArrayList<Category>> listCategories = new MutableLiveData<>();

    public PlantViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<ArrayList<Plant>> getPlants() {
        return listPlants;
    }

    public LiveData<ArrayList<Category>> getCategories() {
        return listCategories;
    }

    public LiveData<Plant> getPlant() {
        return plant;
    }

    public LiveData<Category> getCategory() {
        return category;
    }

    public LiveData<Taxonomy> getTaxonomy() {
        return taxonomy;
    }

    private MutableLiveData<Boolean> isQueryCategoryExhausted = new MutableLiveData<>();

    private MutableLiveData<Boolean> isQueryPlantExhausted = new MutableLiveData<>();

    public MutableLiveData<Boolean> getIsQueryPlantExhausted() {
        return isQueryPlantExhausted;
    }

    public MutableLiveData<Boolean> getIsQueryCategoryExhausted() {
        return isQueryCategoryExhausted;
    }

    private int pageNumberPlant = 1;
    private int pageNumberCategory = 1;
    private boolean isPlantPerformingQuery;
    private boolean isCategoryPerformingQuery;
    private boolean isPlantExhausted;
    private boolean isCategoryExhausted;
    public static final String QUERY_EXHAUSTED = "No more results.";


    public void setTaxonomy(String species){
        taxonomyService.getTaxon(species).enqueue(new Callback<Taxonomy>() {
            @Override
            public void onResponse(Call<Taxonomy> call, Response<Taxonomy> response) {
                if (response.isSuccessful()) {
                    taxonomy.postValue(response.body());
                    Log.d("Taxonomy", "success loading from API");

                } else {
                    int statusCode = response.code();
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<Taxonomy> call, Throwable t) {

            }
        });
    }

    public void setPlants(final String language) {
        plantService.getPlants(language).enqueue(new Callback<PlantResponse>() {
            @Override
            public void onResponse(Call<PlantResponse> call, Response<PlantResponse> response) {
                if (response.isSuccessful()) {
                    ArrayList<Plant> plants = new ArrayList<>(Objects.requireNonNull(response.body()).getPlants());
                    listPlants.postValue(plants);
                    Log.d("Plants", "success loading from API");

                } else {
                    int statusCode = response.code();
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<PlantResponse> call, Throwable t) {
               String b = t.toString();
                Log.d("setPlant", "error loading from API" + b);

            }
        });

    }

    public void setCategories(final String language) {
        plantService.getCategories(language).enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful()) {
                    ArrayList<Category> categories = new ArrayList<>(Objects.requireNonNull(response.body()).getCategories());
                    listCategories.postValue(categories);
                    Log.d("setCategories", "success loading from API");

                } else {
                    int statusCode = response.code();
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                String b = t.toString();
                Log.d("setCategories", "error loading from API" + b);

            }
        });

    }


    public void searchPlants( String query, final int pageNumber, final String language) {
        isPlantPerformingQuery = true;
        plantService.searchPlants(query, String.valueOf(pageNumber), language).enqueue(new Callback<PlantResponse>() {
            @Override
            public void onResponse(Call<PlantResponse> call, Response<PlantResponse> response) {
                if (response.isSuccessful()) {
                    ArrayList<Plant> plants = new ArrayList<>(Objects.requireNonNull(response.body()).getPlants());
                    listPlants.postValue(plants);
                    isPlantPerformingQuery = false;
                    isQueryPlantExhausted.postValue(false);
                    isPlantExhausted = false;
                    Log.d("Plants", "success loading from API");

                } else {
                    int statusCode = response.code();
                    if (statusCode == 404){
                        System.out.println("404");
                    }
                    isQueryPlantExhausted.postValue(true);
                    isPlantExhausted = true;
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<PlantResponse> call, Throwable t) {
                Log.d("searchPlant", "error loading from API");

            }
        });

    }

    public void searchCategories( final int pageNumber, final String language) {
        isCategoryPerformingQuery = true;
        plantService.searchCategories( String.valueOf(pageNumber), language).enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful()) {
                    ArrayList<Category> categories = new ArrayList<>(Objects.requireNonNull(response.body()).getCategories());
                    listCategories.postValue(categories);
                    isCategoryPerformingQuery = false;
                    isQueryCategoryExhausted.postValue(false);
                    isCategoryExhausted = false;
                    Log.d("Categories", "success loading from API");

                } else {
                    int statusCode = response.code();
                    if (statusCode == 404){
                        System.out.println("404");
                    }
                    isQueryCategoryExhausted.postValue(true);
                    isCategoryExhausted = true;
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                Log.d("searchCategories", "error loading from API");

            }
        });

    }

    public void setPlantsFilter( String query, final int category, String order, final int pageNumber, final String language) {
        isPlantPerformingQuery = true;
        plantService.getPlantsFilter(query, String.valueOf(category), order, String.valueOf(pageNumber), language).enqueue(new Callback<PlantResponse>() {
            @Override
            public void onResponse(Call<PlantResponse> call, Response<PlantResponse> response) {
                if (response.isSuccessful()) {
                    ArrayList<Plant> plants = new ArrayList<>(Objects.requireNonNull(response.body()).getPlants());
                    listPlants.postValue(plants);
                    isPlantPerformingQuery = false;
                    isQueryPlantExhausted.postValue(false);
                    isPlantExhausted = false;
                    Log.d("Plants", "success loading from API");

                } else {
                    int statusCode = response.code();
                    if (statusCode == 404){
                        System.out.println("404");
                    }
                    isQueryPlantExhausted.postValue(true);
                    isPlantExhausted = true;
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<PlantResponse> call, Throwable t) {
                Log.d("searchPlant", "error loading from API");

            }
        });

    }

    public void setPlant(String slugText, final String language) {
        plantService.getPlant(slugText, language).enqueue(new Callback<Plant>() {
            @Override
            public void onResponse(Call<Plant> call, Response<Plant> response) {
                if (response.isSuccessful()) {
                    plant.postValue(response.body());
                    Log.d("MainActivity", "posts loaded from API");
                } else {
                    int statusCode = response.code();
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<Plant> call, Throwable t) {
                Log.d("MainActivity", "error loading from API");

            }
        });
    }

    public void setCategory(String slugText, final String language) {
        plantService.getCategory(slugText, language).enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                if (response.isSuccessful()) {
                    category.postValue(response.body());
                    Log.d("MainActivity", "posts loaded from API");
                } else {
                    int statusCode = response.code();
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                Log.d("MainActivity", "error loading from API");

            }
        });
    }

    public void searchPlantsNextPage(String query, final String language){
        Log.d("searchPlantsNextPage", "scroll test is worked");
        if(!isPlantExhausted && !isPlantPerformingQuery){
            pageNumberPlant++;
            searchPlants(query, pageNumberPlant, language);

        }else {
            isQueryPlantExhausted.postValue(true);
        }

    }

    public void searchCategoriesNextPage(final String language){
        Log.d("searchCategoryNextPage", "scroll test is worked");
        if(!isCategoryExhausted && !isCategoryPerformingQuery){
            pageNumberCategory++;
            searchCategories(pageNumberCategory, language);

        }else {
            isQueryCategoryExhausted.postValue(true);
        }

    }

}
