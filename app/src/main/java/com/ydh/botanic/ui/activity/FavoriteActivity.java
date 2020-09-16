package com.ydh.botanic.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.util.ViewPreloadSizeProvider;
import com.ydh.botanic.R;
import com.ydh.botanic.models.Plant;
import com.ydh.botanic.ui.FavoriteClickListener;
import com.ydh.botanic.ui.adapter.FavoritePlantAdapter;
import com.ydh.botanic.viewmodels.FavoriteViewModel;
import com.ydh.botanic.viewmodels.PlantViewModel;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteActivity extends BaseActivity implements FavoriteClickListener {

    @BindView(R.id.nulllayout)
    LinearLayout nullLayout;

    @BindView(R.id.favorite_plant_toolbar)
    Toolbar toolbar;

    @BindView(R.id.favorite_plant_activity)
    RelativeLayout plantListLayout;

    @BindView(R.id.favorite_plant_progressbar)
    ProgressBar progressBar;

    @BindView(R.id.rv_favorite_plant)
    RecyclerView recyclerViewFavorite;

    @BindView(R.id.tvToolbarTitle)
    TextView tvToolbar;

    private PlantViewModel plantViewModel;
    private FavoritePlantAdapter favoritePlantAdapter;
    private FavoriteViewModel favoriteViewModel;

    private String LANGUAGE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        View view = this.getLayoutInflater().inflate(R.layout.activity_register_new_user, null);
        setContentView(R.layout.activity_favorite_plant);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        tvToolbar.setText(R.string.favorite_list_title);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setupViewModeL();
        setupData();
        setupObservers();
        setupFavoriteView();
    }

    private void setupFavoriteView(){
        ViewPreloadSizeProvider<String> viewPreloader = new ViewPreloadSizeProvider<>();

        favoritePlantAdapter = new FavoritePlantAdapter(getApplicationContext(), new ArrayList<>(), new FavoritePlantAdapter.PostItemListener() {
            @Override
            public void onPostClick(String slug, String name) {
                Intent intent = new Intent(FavoriteActivity.this.getApplicationContext(), DetailPlantActivity.class);
                intent.putExtra(DetailPlantActivity.PS, slug);
                intent.putExtra(DetailPlantActivity.SPECIES, name);

                FavoriteActivity.this.startActivity(intent);
            }
        }, this::onFavoriteClick);

        recyclerViewFavorite.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewFavorite.addOnScrollListener(new  RecyclerView.OnScrollListener(){
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerViewFavorite.canScrollVertically(1)){

//                    showLoading(true);
                    favoriteViewModel.searchFavouriteNextPage( LANGUAGE);
                }
            }
        });
        recyclerViewFavorite.setAdapter(favoritePlantAdapter);

    }

    private void setupData() {
        LANGUAGE = Locale.getDefault().toString();
        if (LANGUAGE.equals("in")) {
            LANGUAGE = "id";
        } else {
            LANGUAGE = "en";
        }

        favoriteViewModel.setListFavourite(1, LANGUAGE);

    }

    private void setupViewModeL(){
        favoriteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
        favoriteViewModel.getListFavourite().observe(this, getFavorite);
        favoriteViewModel.getIsQueryExhausted().observe(this, getIsQueryExhausted);

    }

    private void setupObservers(){

    }

    private final Observer<ArrayList<Plant>> getFavorite = new Observer<ArrayList<Plant>>() {
        @Override
        public void onChanged(ArrayList<Plant> items) {
            if (items != null) {
                showLoading(false);
                favoritePlantAdapter.addPlants(items);
                if (favoritePlantAdapter.getItemCount() == 0) {
                    showNull();
                    recyclerViewFavorite.setVisibility(View.GONE);
                }
            } else {
                showLoading(false);
                showNull();
            }
        }
    };

    private final Observer<Boolean>  getIsQueryExhausted = new Observer<Boolean>() {
        @Override
        public void onChanged(Boolean isQueryExhausted) {
            if (isQueryExhausted != null) {
                showLoading(false);
            }
        }
    };

    private final Observer<Boolean>  getIsFavChecked = new Observer<Boolean>() {
        @Override
        public void onChanged(Boolean isQueryExhausted) {
            if (isQueryExhausted != null) {
                showLoading(false);
            }
        }
    };

    private void showNull() {
        nullLayout.setVisibility(View.VISIBLE);
    }

    private Boolean showLoading( Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }
        return state;
    }


    @Override
    public void onFavoriteClick(String slug, int value) {
        favoriteViewModel.setFavorite(slug);
    }
}
