package com.ydh.botanic.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.ydh.botanic.R;
import com.ydh.botanic.models.Category;
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

public class DetailCategoryActivity extends BaseActivity implements FavoriteClickListener {
    public static final String CS = "category_slug";
    public static final String NAME = "category_name";
    public static final String CI = "category_id";

    private String CATEGORY_SLUG;
    private String CATEGORY_NAME;
    private int CATEGORY_ID;

    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;

//    @BindView(R.id.progress_bar_nested_detail)
//    ProgressBar progressBarNested;

    @BindView(R.id.nestedScrollView_detail)
    NestedScrollView nestedScrollView;

    @BindView(R.id.text_name_details)
    TextView categoryName;

//    @BindView(R.id.text_details_categories)
//    TextView plantCategories;
//
//    @BindView(R.id.text_name_details_local)
//    TextView plantLocalName;

    @BindView(R.id.imageView_category_details)
    ImageView categoryImage;

    @BindView(R.id.category_progress_banner)
    ProgressBar progressBar;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.textView_overview_details)
    TextView categoryDescription;

    @BindView(R.id.rv_plant_list)
    RecyclerView rvPlantList;

    PlantViewModel plantViewModel;
    FavoriteViewModel favoriteViewModel;
    private PlantListAdapter plantListAdapter;

    private String LANGUAGE;

//    List<Image> mImage;
//
//    @BindView(R.id.slider_pager)
//    ViewPager sliderPager ;

//    @BindView(R.id.indicator)
//    TabLayout indicator ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_category);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        showCategory(false);
//        showTaxonomy(false);
        showLoading(true);
//        showLoadingNested(true);
        setupViewModeL();
        setupData();
        setupPlantView();
    }

    private void setupPlantView(){
        plantListAdapter = new PlantListAdapter(getApplicationContext(), new ArrayList<>(), new PlantListAdapter.PostItemListener() {
            @Override
            public void onPostClick(String slug, String name) {
                Intent intent = new Intent(getApplicationContext(), DetailPlantActivity.class);
                intent.putExtra(DetailPlantActivity.PS, slug);
                intent.putExtra(DetailPlantActivity.SPECIES, name);

                startActivity(intent);
            }
        }, this::onFavoriteClick);
        rvPlantList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//                rvPlantList.addOnScrollListener(new  RecyclerView.OnScrollListener(){
//                    @Override
//                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                        super.onScrollStateChanged(recyclerView, newState);
//                        if (!rvPlantList.canScrollVertically(1)){
//                    final Handler handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {  if(progressBar.getVisibility() == View.VISIBLE) {
//                            showLoading(false);
//                        }        }
//                    }, 1000);
//                            showLoading(true);
//                            plantViewModel.searchPlantsNextPage("");
//                        }
//                    }
//                });
        rvPlantList.setAdapter(plantListAdapter);
    }

    private void setupData() {
        CATEGORY_SLUG = getIntent().getStringExtra(CS);
        CATEGORY_NAME = getIntent().getStringExtra(NAME);
        CATEGORY_ID = getIntent().getIntExtra(CI, 0);

        getSupportActionBar().setTitle(CATEGORY_NAME);

        LANGUAGE = Locale.getDefault().toString();

        if (LANGUAGE.equals("in")) {
            LANGUAGE = "id";
        } else {
            LANGUAGE = "en";
        }
        plantViewModel.setCategory(CATEGORY_SLUG, LANGUAGE);
//        plantViewModel.setPlants();
        plantViewModel.setPlantsFilter("", CATEGORY_ID,"", 1, LANGUAGE);
    }

    private void setupViewModeL() {
        plantViewModel = ViewModelProviders.of(this).get(PlantViewModel.class);
        plantViewModel.getCategory().observe(this, getCategory);
        plantViewModel.getPlants().observe(this, getPlants);
        favoriteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);

    }


    private final Observer<Category> getCategory = new Observer<Category>() {
        @Override
        public void onChanged(Category category) {
            if (category != null) {

                Glide.with(getApplicationContext())
                        .load(category.getImage())
                        .into(categoryImage);

                categoryDescription.setText(category.getDescription());
                categoryName.setText(category.getName());

                showLoading(false);
                showCategory(true);

            }
        }
    };

    private final Observer<ArrayList<Plant>> getPlants = new Observer<ArrayList<Plant>>() {
        @Override
        public void onChanged(ArrayList<Plant> items) {
            if (items != null) {
                showLoading(false);
                plantListAdapter.addPlants(items);
                if (plantListAdapter.getItemCount() == 0) {
//                    showNull();
                    rvPlantList.setVisibility(View.GONE);
                }
            } else {
                showLoading(false);
//                showNull();
            }
        }
    };

    @Override
    public void onBackPressed() {
        finish();

    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }


    private void showCategory(Boolean state) {
        if (state) {
            categoryName.setVisibility(View.VISIBLE);
            categoryDescription.setVisibility(View.VISIBLE);
        } else {
            categoryName.setVisibility(View.INVISIBLE);
            categoryDescription.setVisibility(View.INVISIBLE);
        }
    }

    private void showTaxonomy(Boolean state) {
        if (state) {
            nestedScrollView.setVisibility(View.VISIBLE);
        } else {
            nestedScrollView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onFavoriteClick(String slug, int value) {
        favoriteViewModel.setFavorite(slug);
    }
}
