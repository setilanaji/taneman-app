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
import com.ydh.botanic.ui.adapter.PlantListAdapter;
import com.ydh.botanic.viewmodels.FavoriteViewModel;
import com.ydh.botanic.viewmodels.PlantViewModel;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlantListActivity extends BaseActivity implements FavoriteClickListener {

    @BindView(R.id.nulllayout)
    LinearLayout nullLayout;

    @BindView(R.id.plant_list_toolbar)
    Toolbar toolbar;

    @BindView(R.id.plant_list_activity)
    RelativeLayout plantListLayout;

    @BindView(R.id.plant_list_progressbar)
    ProgressBar progressBar;

    @BindView(R.id.rv_plant_list)
    RecyclerView recyclerViewPlantList;

    @BindView(R.id.tvToolbarTitle)
    TextView tvToolbar;

    private PlantViewModel plantViewModel;
    private FavoriteViewModel favoriteViewModel;
    private PlantListAdapter plantListAdapter;

    private String LANGUAGE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_list);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        tvToolbar.setText(R.string.plant_list_title);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        showLoading(true);
        setupPlantViewModeL();
        setupPlantData();
        setupPlantView();
    }

    private void setupPlantView() {
        ViewPreloadSizeProvider<String> viewPreloader = new ViewPreloadSizeProvider<>();

        plantListAdapter = new PlantListAdapter(getApplicationContext(), new ArrayList<>(), new PlantListAdapter.PostItemListener() {
            @Override
            public void onPostClick(String slug, String name) {
                Intent intent = new Intent(PlantListActivity.this.getApplicationContext(), DetailPlantActivity.class);
                intent.putExtra(DetailPlantActivity.PS, slug);
                intent.putExtra(DetailPlantActivity.SPECIES, name);

                PlantListActivity.this.startActivity(intent);
            }
        }, this::onFavoriteClick);

        recyclerViewPlantList.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewPlantList.addOnScrollListener(new  RecyclerView.OnScrollListener(){
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerViewPlantList.canScrollVertically(1)){

                    showLoading(true);
                    plantViewModel.searchPlantsNextPage("", LANGUAGE);
                }
            }
        });
        recyclerViewPlantList.setAdapter(plantListAdapter);
    }

    private void setupPlantData() {
        LANGUAGE = Locale.getDefault().toString();
        if (LANGUAGE.equals("in")) {
            LANGUAGE = "id";
        } else {
            LANGUAGE = "en";
        }

        plantViewModel.searchPlants("",1, LANGUAGE);

    }

    private void setupPlantViewModeL() {
        plantViewModel = ViewModelProviders.of(this).get(PlantViewModel.class);
        plantViewModel.getPlants().observe(this, getPlant);
        plantViewModel.getIsQueryPlantExhausted().observe(this, getIsQueryExhausted);
        favoriteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);

    }

    private final Observer<ArrayList<Plant>> getPlant = new Observer<ArrayList<Plant>>() {
        @Override
        public void onChanged(ArrayList<Plant> items) {
            if (items != null) {
                showLoading(false);
                plantListAdapter.addPlants(items);
                if (plantListAdapter.getItemCount() == 0) {
                    showNull();
                    recyclerViewPlantList.setVisibility(View.GONE);
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
        System.out.println("PlantLIst "+ slug);
        favoriteViewModel.setFavorite(slug);
    }
}
