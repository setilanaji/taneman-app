package com.ydh.botanic.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.ydh.botanic.R;
import com.ydh.botanic.models.Category;
import com.ydh.botanic.models.Image;
import com.ydh.botanic.models.Plant;
import com.ydh.botanic.models.Taxonomy;
import com.ydh.botanic.ui.adapter.SliderPagerAdapter;
import com.ydh.botanic.viewmodels.PlantViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailPlantActivity extends BaseActivity {
    public static final String PS = "plant_slug";
    public static final String SPECIES = "plant_species";

    private String PLANT_SLUG;
    private String PLANT_SPECIES;

    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.progress_bar_nested_detail)
    ProgressBar progressBarNested;

    @BindView(R.id.nestedScrollView_detail)
    NestedScrollView nestedScrollView;

    @BindView(R.id.text_name_details)
    TextView plantName;

    @BindView(R.id.text_details_categories)
    TextView plantCategories;

    @BindView(R.id.text_name_details_local)
    TextView plantLocalName;

    @BindView(R.id.imageView_plant_details)
    ImageView plantImage;

    @BindView(R.id.plant_progress_banner)
    ProgressBar progressBar;

    @BindView(R.id.textView_kingdom_details)
    TextView kingdom;

    @BindView(R.id.textView_order_details)
    TextView order;

    @BindView(R.id.textView_genus_details)
    TextView genus;

    @BindView(R.id.textView_species_details)
    TextView species;

    @BindView(R.id.textView_family_details)
    TextView family;

    @BindView(R.id.textView_phylum_details)
    TextView phylum;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.textView_overview_details)
    TextView plantDescription;

    PlantViewModel plantViewModel;
    List<Image> mImage;

    @BindView(R.id.slider_pager)
    ViewPager sliderPager ;

    @BindView(R.id.indicator)
    TabLayout indicator ;

    private String LANGUAGE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_plant);
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
        showPlant(false);
        showTaxonomy(false);
        showLoading(true);
        showLoadingNested(true);
        setupViewModeL();
        setupData();
    }


    private void setupData() {
        PLANT_SLUG = getIntent().getStringExtra(PS);
        PLANT_SPECIES = getIntent().getStringExtra(SPECIES);
        getSupportActionBar().setTitle(PLANT_SPECIES);
        LANGUAGE = Locale.getDefault().toString();

        if (LANGUAGE.equals("in")) {
            LANGUAGE = "id";
        } else {
            LANGUAGE = "en";
        }

        plantViewModel.setPlant(PLANT_SLUG, LANGUAGE);
        plantViewModel.setTaxonomy(PLANT_SPECIES);
    }

    private void setupViewModeL() {
        plantViewModel = ViewModelProviders.of(this).get(PlantViewModel.class);
        plantViewModel.getPlant().observe(this, getPlant);
        plantViewModel.getTaxonomy().observe(this, getTaxonomy);
    }

    private void initSlider() {
        // prepare a list of slider

        System.out.println("/n/n " + mImage);

        SliderPagerAdapter adapter = new SliderPagerAdapter(this, mImage);
        sliderPager.setAdapter(adapter);
        // Set Up Timer
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(), 4000 , 5000 );
        indicator.setupWithViewPager(sliderPager,true);
    }

    class SliderTimer extends TimerTask {
        @Override
        public void run() {
            DetailPlantActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (sliderPager.getCurrentItem()<mImage.size()-1){
                        sliderPager.setCurrentItem(sliderPager.getCurrentItem()+1);
                    } else {
                        sliderPager.setCurrentItem(0);
                    }
                }
            });
        }
    }


    private final Observer<Plant> getPlant = new Observer<Plant>() {
        @Override
        public void onChanged(Plant plant) {
            if (plant != null) {

//                Glide.with(getApplicationContext())
//                        .load(plant.getImageUrl())
//                        .into(plantImage);

                plantDescription.setText(plant.getCharacteristic());
                plantName.setText(plant.getName());
                plantLocalName.setText(plant.getLocal());
                List<Category> mCategory = plant.getCategories();
                mImage = new ArrayList<>();
                Image profile = new Image(1, plant.getImageUrl());
                mImage.add(profile);
                mImage.addAll(plant.getImages());

                System.out.println("getPlant" +  mImage);

                String categories = "";
                if (!mCategory.isEmpty()){
                    for (Category cat : mCategory){
                        categories += cat.getName() + ", ";
                    }
                    plantCategories.setText(categories);
                }
                else {
                    plantCategories.setText("-");
                }
                initSlider();


                showLoading(false);
                showPlant(true);

            }
        }
    };

    private final Observer<Taxonomy> getTaxonomy = new Observer<Taxonomy>() {
        @Override
        public void onChanged(Taxonomy taxonomy) {
            if (taxonomy != null) {

                phylum.setText(taxonomy.getPhylum());
                kingdom.setText(taxonomy.getKingdom());
                order.setText(taxonomy.getOrder());
                family.setText(taxonomy.getFamily());
                genus.setText(taxonomy.getGenus());
                species.setText(taxonomy.getSpecies());
                showLoadingNested(false);
                showTaxonomy(true);
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
    private void showLoadingNested(Boolean state) {
        if (state) {
            progressBarNested.setVisibility(View.VISIBLE);
        } else {
            progressBarNested.setVisibility(View.GONE);
        }
    }

    private void showPlant(Boolean state) {
        if (state) {
            plantName.setVisibility(View.VISIBLE);
            plantDescription.setVisibility(View.VISIBLE);
        } else {
            plantName.setVisibility(View.INVISIBLE);
            plantDescription.setVisibility(View.INVISIBLE);
        }
    }

    private void showTaxonomy(Boolean state) {
        if (state) {
            nestedScrollView.setVisibility(View.VISIBLE);
        } else {
            nestedScrollView.setVisibility(View.INVISIBLE);
        }
    }
}
