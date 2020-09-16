package com.ydh.botanic.ui.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.ydh.botanic.utils.UserSessionManager;

import java.util.Locale;

public abstract class BaseActivity extends AppCompatActivity {

    protected static final String TAG_FRAGMENT_HOME = "fragment_home";
    protected static final String TAG_FRAGMENT_FAVORITE = "fragment_favorite";
    protected static final String TAG_FRAGMENT_SEARCH = "fragment_search";


    public void hideKeyword(Window window){
        window.setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public void colorStatusBar(Window window, int color, Boolean isClearColor) {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        View view = window.getDecorView();
        if(isClearColor){
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            view.setSystemUiVisibility(View.GONE);
        }
//        window.setStatusBarColor(getColor(color));
    }

    public void hideKeyboardFrom(Context context, View view) {
//        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(PreLoginActivity.INPUT_METHOD_SERVICE);
//        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void loadingExecutor(Boolean isLoading, ProgressBar progressBar, FrameLayout frameLayout, Button button) {
        if (isLoading != null) {
            if (isLoading) {
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                Sprite threeBounce = new ThreeBounce();
                progressBar.setIndeterminateDrawable(threeBounce);
                button.setVisibility(View.INVISIBLE);
                frameLayout.setVisibility(View.VISIBLE);
            } else {
                Sprite threeBounce = new ThreeBounce();
                progressBar.setIndeterminateDrawable(threeBounce);
                button.setVisibility(View.VISIBLE);
                frameLayout.setVisibility(View.INVISIBLE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(updateBaseContextLocale(base));
    }

    private Context updateBaseContextLocale(Context context) {
        String language = UserSessionManager.getLanguageApp(context); // Helper method to get saved language from SharedPreferences
        System.out.println("Base Activity 78 "+language);
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
//            System.out.println("Base Activity 83 1 ");
            return updateResourcesLocale(context, locale);
        }

//        System.out.println("Base Activity 87 2 ");

        return updateResourcesLocaleLegacy(context, locale);
    }

    @TargetApi(Build.VERSION_CODES.N_MR1)
    private Context updateResourcesLocale(Context context, Locale locale) {
        Configuration configuration = new Configuration(context.getResources().getConfiguration());
        configuration.setLocale(locale);
        return context.createConfigurationContext(configuration);
    }

    @SuppressWarnings("deprecation")
    private Context updateResourcesLocaleLegacy(Context context, Locale locale) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return context;
    }

//    @Override
//    public void applyOverrideConfiguration(Configuration overrideConfiguration) {
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Build.VERSION.SDK_INT <= Build.VERSION_CODES.N_MR1) {
////            // update overrideConfiguration with your locale
////            setLocale(overrideConfiguration) // you will need to implement this
////        }
//        super.applyOverrideConfiguration(overrideConfiguration);
//    }

//    protected void pushFragments(String tag, Fragment fragment) {
//
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//
//        if (getSupportFragmentManager().findFragmentByTag(tag) == null) {
//            ft.add(R.id.content_home_drawer, fragment, tag);
//        }
//
//        Fragment fragmentHome = getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_HOME);
//        Fragment fragmentFavorite = getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_FAVORITE);
//        Fragment fragmentSearch = getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_SEARCH);
//
//        if (fragmentHome != null) {
//            ft.hide(fragmentHome);
//        }
//        if (fragmentFavorite != null) {
//            ft.hide(fragmentFavorite);
//        }
//        if (fragmentSearch != null) {
//            ft.hide(fragmentSearch);
//        }
//
//        if (tag == TAG_FRAGMENT_HOME) {
//            if (fragmentHome != null) {
//                ft.show(fragmentHome);
//            }
//        }
//        if (tag == TAG_FRAGMENT_FAVORITE) {
//            if (fragmentFavorite != null) {
//                ft.show(fragmentFavorite);
//            }
//        }
//
//        if (tag == TAG_FRAGMENT_SEARCH) {
//            if (fragmentSearch != null) {
//                ft.show(fragmentSearch);
//            }
//        }
//        ft.commitAllowingStateLoss();
//    }

}
