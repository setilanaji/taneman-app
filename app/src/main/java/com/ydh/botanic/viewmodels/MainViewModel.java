package com.ydh.botanic.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.ydh.botanic.utils.UserSessionManager;
import com.ydh.botanic.utils.singleton.SingletonAccessToken;

public class MainViewModel extends BaseViewModel {

    public MainViewModel(@NonNull Application application) {
        super(application);
        authenticationState.setValue(AuthenticationState.UNKNOWN);
    }

    public enum AuthenticationState {
        UNAUTHENTICATED,        // Initial state, the user needs to authenticate
        AUTHENTICATED,          // The user has authenticated successfully
        UNKNOWN  // Authentication failed
    }

    public final MutableLiveData<AuthenticationState> authenticationState =
            new MutableLiveData<>();



    public void authenticate() {
        System.out.println(UserSessionManager.getLoggedInStatus(getApplication().getApplicationContext()));

        if (UserSessionManager.getLoggedInStatus(getApplication().getApplicationContext())) {
            authenticationState.setValue(AuthenticationState.AUTHENTICATED);
            SingletonAccessToken.setAccessTokenReceived(UserSessionManager.getLoggedInKeyToken(getApplication().getApplicationContext()));
        } else {
            authenticationState.setValue(AuthenticationState.UNAUTHENTICATED);
        }
    }

    public void refuseAuthentication() {
        authenticationState.setValue(AuthenticationState.UNAUTHENTICATED);
    }



}
