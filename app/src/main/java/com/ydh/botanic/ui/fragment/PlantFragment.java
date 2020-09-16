package com.ydh.botanic.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ydh.botanic.R;
import com.ydh.botanic.models.Category;
import com.ydh.botanic.ui.adapter.CategoryAdapter;
import com.ydh.botanic.utils.VerticalSpaceItemDecoration;
import com.ydh.botanic.viewmodels.PlantViewModel;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

//import com.example.watchid.Activity.DetailPlantActivity;
//import com.example.watchid.Adapter.DiscoverAdapter;
//import com.example.watchid.Adapter.PopularAdapter;
//import com.example.watchid.Model.Movie;
//import com.example.watchid.Model.MoviePopular;
//import com.example.watchid.ViewModel.MovieViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlantFragment extends Fragment {

    @BindView(R.id.rvCategory)
    RecyclerView rvCategory;

    @BindView(R.id.plantProgressbar)
    ProgressBar progressBar;

    PlantViewModel plantViewModel;

    private CategoryAdapter categoryAdapter;


//
    public PlantFragment() {
    }
//
//
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_plant, container, false);
    }
//
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        showLoading(true);
//        setCategoryhide();
        setupViewModel();
        setupData();
        setupCategoryView();
    }
//
    private void setupViewModel() {
        plantViewModel = ViewModelProviders.of(this).get(PlantViewModel.class);
        plantViewModel.getCategories().observe(this, getCategories);
    }
//
    private void setupData() {
        String LANGUAGE = Locale.getDefault().toString();
        if (LANGUAGE.equals("in")) {
            LANGUAGE = "id";
        } else {
            LANGUAGE = "en";
        }
        plantViewModel.setCategories(LANGUAGE);
    }
//
    private void setupCategoryView() {
        categoryAdapter = new CategoryAdapter(getContext(), new ArrayList<>(), new CategoryAdapter.PostItemListener() {
            @Override
            public void onPostClick(String slug, String name, int id) {
//            Intent intent = new Intent(getContext(), DetailPlantActivity.class);
//            intent.putExtra(DetailPlantActivity.MID, id);
//            startActivity(intent);
            }
        });

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvCategory.setLayoutManager(llm);
        rvCategory.addItemDecoration(new VerticalSpaceItemDecoration(getResources().getDimension(R.dimen.spaceVerticalRecycleView)));

        rvCategory.setAdapter(categoryAdapter);
    }
//
//
    private final Observer<ArrayList<Category>> getCategories = new Observer<ArrayList<Category>>() {
        @Override
        public void onChanged(ArrayList<Category> categories) {
            if (categories != null) {
                categoryAdapter.addCategory(categories);
                showLoading(false);
            }
        }
    };

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
            rvCategory.setVisibility(View.INVISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            rvCategory.setVisibility(View.VISIBLE);
        }
    }

//    private void setCategoryhide() {
//
//    }
//
//    private void setCategoryShow() {
//
//    }
}
