package com.ydh.botanic.ui.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.sdsmdg.tastytoast.TastyToast;
import com.ydh.botanic.R;
import com.ydh.botanic.ui.activity.ChangePasswordActivity;
import com.ydh.botanic.ui.activity.FavoriteActivity;
import com.ydh.botanic.ui.activity.LoginUserActivity;
import com.ydh.botanic.ui.activity.MainActivity;
import com.ydh.botanic.utils.UserSessionManager;
import com.ydh.botanic.viewmodels.MainViewModel;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.ydh.botanic.utils.Constants.LANGUAGE_LIST;

//import com.example.watchid.Activity.DetailPlantActivity;
//import com.example.watchid.Adapter.FavoriteAdapter;
//import com.example.watchid.Callback.LoadFavoriteCallback;
//import com.example.watchid.Database.FavoriteHelper;
//import com.example.watchid.Model.Favorite;

//import static com.example.watchid.Database.DatabaseContract.FavoriteColumns.CONTENT_URI;
//import static com.example.watchid.Helper.MappingHelper.getFavoriteList;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {

    private static final String EXTRA_STATE = "EXTRA_STATE";

    @BindView(R.id.textView_username_user)
    TextView username;

    @BindView(R.id.change_password_user)
    LinearLayout linearLayoutChangePass;

    @BindView(R.id.bookmark_user)
    LinearLayout linearLayoutBookmark;

    @BindView(R.id.logout_user)
    LinearLayout logout;

    @BindView(R.id.textView_emai_user)
    TextView email;

    MainViewModel mainViewModel;


    public UserFragment() {
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

        @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        showLoading(true);
        setupViewModel();
        setupObservers();
        setupData();
        setupListeners();
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.user_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_language:
                showChangeLanguageIDalog();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void showChangeLanguageIDalog() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        mBuilder.setTitle(R.string.change_language_label);
        mBuilder.setSingleChoiceItems(LANGUAGE_LIST, setChecked(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        Locale locale = new Locale("in");
                        UserSessionManager.setLanguageApp(getContext(), "in");
                        setLocale(locale);
                        break;
                    case 1:
                        locale = new Locale("en");
                        UserSessionManager.setLanguageApp(getContext(), "en");
                        setLocale(locale);
                        break;
                }
            }
        });
        mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }

    @SuppressWarnings("deprecation")
    private void setLocale(Locale locale){
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            configuration.setLocale(locale);
        } else{
            configuration.locale=locale;
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N){
            getContext().createConfigurationContext(configuration);
        } else {
            resources.updateConfiguration(configuration,displayMetrics);
        }
    }

    private int setChecked(){
        String language = UserSessionManager.getLanguageApp(getContext());
        if (language.equals("in")){
            return 0;
        } else {
            return 1;
        }
    }




    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item=menu.findItem(R.id.action_search);
        if(item!=null)
            item.setVisible(false);
    }

    private void setupObservers() {
        mainViewModel.authenticationState.observe(this, loggedUserStatus);
    }

    private void setupViewModel() {
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

    }

    private void showLoading(boolean b) {
    }

    private void setupData() {
        email.setText(UserSessionManager.getLoggedInKeyEmail(getContext()));
        username.setText(UserSessionManager.getLoggedInUser(getContext()));

    }

    private void setupListeners() {
        linearLayoutChangePass.setOnClickListener(toChangePassListener);
        linearLayoutBookmark.setOnClickListener(toBookmarkListener);
        logout.setOnClickListener(toLogoutUserListener);

    }

    private View.OnClickListener toLogoutUserListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            UserSessionManager.setLoggedInStatus(getContext(), false);
            mainViewModel.authenticationState.setValue(MainViewModel.AuthenticationState.UNAUTHENTICATED);
        }
    };


    private Observer<MainViewModel.AuthenticationState> loggedUserStatus = new Observer<MainViewModel.AuthenticationState>() {
        @Override
        public void onChanged(MainViewModel.AuthenticationState authenticationState) {
            switch (authenticationState){
                case UNAUTHENTICATED:
                    TastyToast.makeText(getContext(), "You are not logged in, please login first", TastyToast.LENGTH_LONG, TastyToast.WARNING)
                            .setGravity(Gravity.CENTER,0,500);
                    Intent intent = new Intent(getActivity(), LoginUserActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };

    private View.OnClickListener toChangePassListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener toBookmarkListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), FavoriteActivity.class);
            startActivity(intent);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }
    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }


}
