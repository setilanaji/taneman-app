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
import com.ydh.botanic.models.Category;
import com.ydh.botanic.ui.adapter.CategoryListAdapter;
import com.ydh.botanic.viewmodels.PlantViewModel;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryListActivity  extends BaseActivity {

    @BindView(R.id.nulllayout)
    LinearLayout nullLayout;

    @BindView(R.id.category_list_toolbar)
    Toolbar toolbar;

    @BindView(R.id.category_list_activity)
    RelativeLayout categoryListLayout;

    @BindView(R.id.category_list_progressbar)
    ProgressBar progressBar;

    @BindView(R.id.rv_category_list)
    RecyclerView recyclerViewCategoryList;

    @BindView(R.id.tvToolbarTitle)
    TextView tvToolbar;

    private PlantViewModel plantViewModel;
    private CategoryListAdapter categoryListAdapter;
    private String LANGUAGE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        tvToolbar.setText(R.string.category_list_title);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        showLoading(true);
        setupPlantViewModeL();
        setupCategoryData();
        setupCategoryView();
    }

    private void setupCategoryView() {
        ViewPreloadSizeProvider<String> viewPreloader = new ViewPreloadSizeProvider<>();

        categoryListAdapter = new CategoryListAdapter(getApplicationContext(), new ArrayList<>(), new CategoryListAdapter.PostItemListener() {
            @Override
            public void onPostClick(String slug, String name, int id) {
                Intent intent = new Intent(CategoryListActivity.this.getApplicationContext(), DetailCategoryActivity.class);
                intent.putExtra(DetailCategoryActivity.CS, slug);
                intent.putExtra(DetailCategoryActivity.NAME, name);
                intent.putExtra(DetailCategoryActivity.CI, id);
                CategoryListActivity.this.startActivity(intent);
            }
        });

        recyclerViewCategoryList.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCategoryList.addOnScrollListener(new  RecyclerView.OnScrollListener(){
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerViewCategoryList.canScrollVertically(1)){
                    showLoading(true);
                    plantViewModel.searchCategoriesNextPage(LANGUAGE);
                }
            }
        });
        recyclerViewCategoryList.setAdapter(categoryListAdapter);
    }

    private void setupCategoryData() {
        LANGUAGE = Locale.getDefault().toString();

        if (LANGUAGE.equals("in")) {
            LANGUAGE = "id";
        } else {
            LANGUAGE = "en";
        }
        plantViewModel.setCategories(LANGUAGE);

    }

    private void setupPlantViewModeL() {
        plantViewModel = ViewModelProviders.of(this).get(PlantViewModel.class);
        plantViewModel.getCategories().observe(this, getCategories);
        plantViewModel.getIsQueryCategoryExhausted().observe(this, getIsQueryExhausted);

    }

    private final Observer<ArrayList<Category>> getCategories = new Observer<ArrayList<Category>>() {
        @Override
        public void onChanged(ArrayList<Category> items) {
            if (items != null) {
                showLoading(false);
                categoryListAdapter.addCategory(items);
                if (categoryListAdapter.getItemCount() == 0) {
                    showNull();
                    recyclerViewCategoryList.setVisibility(View.GONE);
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
}
