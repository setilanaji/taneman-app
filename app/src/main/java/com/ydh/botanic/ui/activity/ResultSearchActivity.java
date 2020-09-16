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
import com.ydh.botanic.ui.adapter.ResultAdapter;
import com.ydh.botanic.viewmodels.FavoriteViewModel;
import com.ydh.botanic.viewmodels.PlantViewModel;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultSearchActivity extends BaseActivity implements FavoriteClickListener {

    static final String KEYWORD = "keyword";
    static final String INDEX = "index";

    @BindView(R.id.nulllayout)
    LinearLayout nullLayout;

    @BindView(R.id.rvSearch)
    RecyclerView rvSearch;

    @BindView(R.id.resultProgressbar)
    ProgressBar progressBar;

    @BindView(R.id.resultToolbar)
    Toolbar toolbar;

    @BindView(R.id.resultActivity)
    RelativeLayout resultActivity;

    @BindView(R.id.tvToolbarTitle)
    TextView tvToolbar;

    private String QUERY;
    private ResultAdapter adapter;
    private PlantViewModel plantViewModel;
    private FavoriteViewModel favoriteViewModel;

    private String LANGUAGE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_search);
        ButterKnife.bind(this);

        QUERY = getIntent().getStringExtra(KEYWORD);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tvToolbar.setText((getResources().getString(R.string.result) + " \"" + QUERY + "\""));
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
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

        adapter = new ResultAdapter(getApplicationContext(), new ArrayList<>(), new ResultAdapter.PostItemListener() {
            @Override
            public void onPostClick(String slug, String name) {
                Intent intent = new Intent(ResultSearchActivity.this.getApplicationContext(), DetailPlantActivity.class);
                intent.putExtra(DetailPlantActivity.PS, slug);
                intent.putExtra(DetailPlantActivity.SPECIES, name);

                ResultSearchActivity.this.startActivity(intent);
            }
        }, this::onFavoriteClick);
//        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        rvSearch.setLayoutManager(new LinearLayoutManager(this));
        rvSearch.addOnScrollListener(new  RecyclerView.OnScrollListener(){
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!rvSearch.canScrollVertically(1)){
                    showLoading(true);
                    plantViewModel.searchPlantsNextPage(QUERY, LANGUAGE);
                }
            }
        });
        rvSearch.setAdapter(adapter);
    }

    private void setupPlantData() {
        LANGUAGE = Locale.getDefault().toString();

        if (LANGUAGE.equals("in")) {
            LANGUAGE = "id";
        } else {
            LANGUAGE = "en";
        }

        plantViewModel.searchPlants(QUERY, 1, LANGUAGE);

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
                adapter.addPlants(items);
                if (adapter.getItemCount() == 0) {
                    showNull();
                    rvSearch.setVisibility(View.GONE);
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

    private void showLoading( Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onFavoriteClick(String slug, int value) {
        favoriteViewModel.setFavorite(slug);
    }
}