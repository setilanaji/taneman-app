package com.ydh.botanic.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sdsmdg.tastytoast.TastyToast;
import com.ydh.botanic.R;
import com.ydh.botanic.ui.fragment.HomeFragment;
import com.ydh.botanic.ui.fragment.UserFragment;
import com.ydh.botanic.viewmodels.MainViewModel;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements SearchView.OnQueryTextListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tvToolbarTitle)
    TextView tvToolbar;

    String index = "0";

    @BindView(R.id.first_menu_item)
    ImageButton imageButtonHome;

    @BindView(R.id.second_menu_item)
    ImageButton imageButtonProfile;

    @BindView(R.id.fab_scan)
    FloatingActionButton fabScan;

    MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        BottomNavigationView navView = findViewById(R.id.nav_view);
//        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setupViewModeL();
        setupObservers();
        checkLogin();
        setupView();


        if (savedInstanceState == null) {
//            navView.setSelectedItemId(R.id.navigation_home);
            Fragment fragment;
            fragment = new HomeFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment, fragment.getClass().getSimpleName())
                    .commit();
        }
//        String lang = UserSessionManager.getLanguageApp(getApplicationContext());
//        Toast.makeText(getApplicationContext(), lang, Toast.LENGTH_LONG).show();


    }


    private void checkLogin() {
        mainViewModel.authenticate();

    }

    private void setupObservers() {
        mainViewModel.authenticationState.observe(this, loggedUserStatus);

    }

    private void setupViewModeL() {
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

    }

    private void setupView() {
//         imageButtonHome = findViewById(R.id.first_menu_item);
        imageButtonHome.setOnClickListener(imageButtonHomeClickListener);
        imageButtonHome.setSelected(true);
        imageButtonHome.setFocusable(true);
//        imageButtonProfile = findViewById(R.id.second_menu_item);
        imageButtonProfile.setOnClickListener(imageButtonProfileClickListener);
//        fabScan = findViewById(R.id.fab_scan);
        fabScan.setOnClickListener(fabScanClickListener);
        setupToolbar();
    }

    private Observer<MainViewModel.AuthenticationState> loggedUserStatus = new Observer<MainViewModel.AuthenticationState>() {
        @Override
        public void onChanged(MainViewModel.AuthenticationState authenticationState) {
            switch (authenticationState) {
                case UNAUTHENTICATED:
                    TastyToast.makeText(getApplicationContext(), getString(R.string.logoutMessage), TastyToast.LENGTH_LONG, TastyToast.WARNING)
                            .setGravity(Gravity.CENTER, 0, 500);
                    Intent intent = new Intent(MainActivity.this, LoginUserActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };

    private ImageButton.OnClickListener imageButtonHomeClickListener = new ImageButton.OnClickListener() {

        @Override
        public void onClick(View v) {
            changeFragment(1);
            imageButtonProfile.setSelected(false);
            imageButtonProfile.setFocusable(false);
            v.setSelected(true);
            v.setFocusable(true);
        }
    };

    private FloatingActionButton.OnClickListener fabScanClickListener = new FloatingActionButton.OnClickListener(){

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, ScannerActivity.class);
            startActivity(intent);
        }
    };

    private ImageButton.OnClickListener imageButtonProfileClickListener = new ImageButton.OnClickListener() {

        @Override
        public void onClick(View v) {
            changeFragment(2);
            imageButtonHome.setSelected(false);
            imageButtonHome.setFocusable(false);
            v.setSelected(true);
            v.setFocusable(true);

        }
    };


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    fragment = new HomeFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, fragment, fragment.getClass().getSimpleName())
                            .commit();
                    return true;
                case R.id.navigation_user:
                    fragment = new UserFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, fragment, fragment.getClass().getSimpleName())

                            .commit();

                    return true;
            }
            return false;
        }
    };

    private void changeFragment(int state) {
        Fragment fragment;
        switch (state) {
            case 1:
                fragment = new HomeFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, fragment, fragment.getClass().getSimpleName())
                        .commit();
                break;
            case 2:
                fragment = new UserFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, fragment, fragment.getClass().getSimpleName())
                        .commit();

                break;
        }

    }


    private void setupToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setElevation(1);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tvToolbar.setText(getResources().getString(R.string.app_name));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_search:
                SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);

                SearchView.SearchAutoComplete searchAutoComplete =
                        (SearchView.SearchAutoComplete) searchView.findViewById(androidx.appcompat.R.id.search_src_text);
                searchAutoComplete.setHintTextColor(getResources().getColor(R.color.bottom_inactive));
                searchAutoComplete.setTextColor(getResources().getColor(R.color.colorBlack));
                searchAutoComplete.setPadding(32,16,32,16);
//                searchAutoComplete.setBackgroundColor(getResources().getColor(R.color.white));
                searchAutoComplete.setBackground(getResources().getDrawable(R.drawable.search_background));

                ImageView searchClose = searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
                searchClose.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);

                ImageView searchIcon = searchView.findViewById(androidx.appcompat.R.id.search_mag_icon);
                searchIcon.setColorFilter(getResources().getColor(R.color.white),
                        android.graphics.PorterDuff.Mode.SRC_IN);

                final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
                upArrow.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
                getSupportActionBar().setHomeAsUpIndicator(upArrow);
                searchView.setOnQueryTextListener(this);
                break;

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        Intent i = new Intent(MainActivity.this, ResultSearchActivity.class);
        i.putExtra(ResultSearchActivity.KEYWORD, query);
        Log.i("INDEX", " " + index);
        startActivity(i);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainViewModel.removeObserver();
    }
}
