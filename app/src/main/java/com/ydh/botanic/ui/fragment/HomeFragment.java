package com.ydh.botanic.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.ydh.botanic.R;
import com.ydh.botanic.models.Category;
import com.ydh.botanic.models.Plant;
import com.ydh.botanic.ui.FavoriteClickListener;
import com.ydh.botanic.ui.activity.CategoryListActivity;
import com.ydh.botanic.ui.activity.DetailCategoryActivity;
import com.ydh.botanic.ui.activity.DetailPlantActivity;
import com.ydh.botanic.ui.activity.PlantListActivity;
import com.ydh.botanic.ui.adapter.CategoryAdapter;
import com.ydh.botanic.ui.adapter.DiscoverAdapter;
import com.ydh.botanic.ui.adapter.PlantAdapter;
import com.ydh.botanic.utils.CustomSwapToRefresh;
import com.ydh.botanic.utils.HorizontalSpaceItemDecoration;
import com.ydh.botanic.utils.UserSessionManager;
import com.ydh.botanic.viewmodels.FavoriteViewModel;
import com.ydh.botanic.viewmodels.PlantViewModel;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements FavoriteClickListener {


    @BindView(R.id.pdRvPlant)
    RecyclerView pdRvPlant;

    @BindView(R.id.rvCategoryhome)
    RecyclerView rvCategory;

    @BindView(R.id.homeProgressbar)
    ProgressBar progressBar;

    @BindView(R.id.swiperefresh)
    CustomSwapToRefresh mSwipeRefreshLayout;

    @BindView(R.id.textView_hello_home)
    TextView textViewHelloUser;

    @BindView(R.id.title_plant_linear)
    ConstraintLayout constraintLayoutPlant;

    @BindView(R.id.title_categories_linear)
    ConstraintLayout constraintLayoutCategories;

    @BindView(R.id.scrollView_home)
    ScrollView scrollViewHome;


    private PlantViewModel plantViewModel;
    private FavoriteViewModel favoriteViewModel;
    private DiscoverAdapter discoverAdapter;
    private PlantAdapter plantAdapter;
    private CategoryAdapter categoryAdapter;
    private String LANGUAGE;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        showLoading(true);
        setupViewModeL();
        setupView();
        setupData();
        setupPlantView();
        setupCategoryView();
    }

    private void setupView(){
        mSwipeRefreshLayout.setOnRefreshListener(swipeOnRefreshListener);
        textViewHelloUser.setText("Hello, "+ UserSessionManager.getLoggedInUser(getContext()));
        constraintLayoutPlant.setOnClickListener(plantsOnClickListener);
        constraintLayoutCategories.setOnClickListener(categoriesOnClickListener);
    }

    private CustomSwapToRefresh.OnRefreshListener swipeOnRefreshListener = new CustomSwapToRefresh.OnRefreshListener() {
        @Override
        public void onRefresh() {
//            setupData();
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {  if(mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }        }
            }, 1000);
        }
    };

    private ConstraintLayout.OnClickListener plantsOnClickListener = new ConstraintLayout.OnClickListener(){

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(HomeFragment.this.getContext(), PlantListActivity.class);
            HomeFragment.this.startActivity(intent);
        }
    };

    private ConstraintLayout.OnClickListener categoriesOnClickListener = new ConstraintLayout.OnClickListener(){

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(HomeFragment.this.getContext(), CategoryListActivity.class);
            HomeFragment.this.startActivity(intent);
        }
    };

    private void setupViewModeL() {
        plantViewModel = ViewModelProviders.of(this).get(PlantViewModel.class);
        plantViewModel.getPlants().observe(this, getListPlants);
        plantViewModel.getCategories().observe(this, getCategories);
        favoriteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
//        plantViewModel.getIsQueryCategoryExhausted().observe(this, getIsQueryExhausted);
//        plantViewModel.getIsQueryPlantExhausted().observe(this, getIsQueryExhausted);

    }

    private void setupData() {
        LANGUAGE = Locale.getDefault().toString();
        if (LANGUAGE.equals("in")) {
            LANGUAGE = "id";
        } else {
            LANGUAGE = "en";
        }
        plantViewModel.setPlants(LANGUAGE);
        plantViewModel.setCategories(LANGUAGE);
    }

    private void setupDiscoverView() {
        discoverAdapter = new DiscoverAdapter(getContext(), new ArrayList<>(), (slug, name) -> {
            Intent intent = new Intent(HomeFragment.this.getContext(), DetailPlantActivity.class);
            intent.putExtra(DetailPlantActivity.PS, slug);
            intent.putExtra(DetailPlantActivity.SPECIES, name);
            HomeFragment.this.startActivity(intent);
        });

    }

    private void setupPlantView(){
        plantAdapter = new PlantAdapter(getContext(), new ArrayList<>(), (slug, name) -> {
            Intent intent = new Intent(getContext(), DetailPlantActivity.class);
            intent.putExtra(DetailPlantActivity.PS, slug);
            intent.putExtra(DetailPlantActivity.SPECIES, name);
            startActivity(intent);
        }, this::onFavoriteClick);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        pdRvPlant.setLayoutManager(llm);
        pdRvPlant.addItemDecoration(new HorizontalSpaceItemDecoration(getResources().getDimension(R.dimen.spaceHorizontalRecycleView)));
        pdRvPlant.addOnScrollListener(new  RecyclerView.OnScrollListener(){
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!pdRvPlant.canScrollHorizontally(1)){
//                    showLoading(true);
                    plantViewModel.searchPlantsNextPage("",LANGUAGE );
                }
            }
        });
        pdRvPlant.setAdapter(plantAdapter);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(pdRvPlant);
    }

    private void setupCategoryView() {
        categoryAdapter = new CategoryAdapter(getContext(), new ArrayList<>(), new CategoryAdapter.PostItemListener() {
            @Override
            public void onPostClick(String slug, String name, int id) {
                Intent intent = new Intent(getContext(), DetailCategoryActivity.class);
                intent.putExtra(DetailCategoryActivity.CS, slug);
                intent.putExtra(DetailCategoryActivity.NAME, name);
                intent.putExtra(DetailCategoryActivity.CI, id);
                startActivity(intent);
            }
        });

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvCategory.setLayoutManager(llm);
        rvCategory.addItemDecoration(new HorizontalSpaceItemDecoration(getResources().getDimension(R.dimen.spaceVerticalRecycleView)));
        rvCategory.addOnScrollListener(new  RecyclerView.OnScrollListener(){
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!rvCategory.canScrollHorizontally(1)){
//                    showLoading(true);
                    plantViewModel.searchCategoriesNextPage( LANGUAGE );
                }
            }
        });
        rvCategory.setAdapter(categoryAdapter);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(rvCategory);
    }

    private final Observer<ArrayList<Category>> getCategories = new Observer<ArrayList<Category>>() {
        @Override
        public void onChanged(ArrayList<Category> categories) {
            if (categories != null) {
                categoryAdapter.addCategory(categories);
                showLoading(false);
            }
        }
    };


    private final Observer<ArrayList<Plant>> getPlants = new Observer<ArrayList<Plant>>() {
        @Override
        public void onChanged(ArrayList<Plant> plants) {
            if (plants != null) {
                discoverAdapter.addPlant(plants);
                showLoading(false);
            }
        }
    };

    private final Observer<ArrayList<Plant>> getListPlants = new Observer<ArrayList<Plant>>() {
        @Override
        public void onChanged(ArrayList<Plant> plants) {
            if (plants != null) {
                plantAdapter.addPlant(plants);
                showLoading(false);
            }
        }
    };



    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);

        }
    }

    @Override
    public void onFavoriteClick(String slug, int value) {
        favoriteViewModel.setFavorite(slug);
    }
}