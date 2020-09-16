package com.ydh.botanic.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public abstract class BaseViewModel extends AndroidViewModel {
    protected int SUCCESS_CODE = 200;
    protected int SESSION_EXPIRED_CODE = 401;
    protected int OTHER_ERROR_CODE = 500;

    protected MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    protected MutableLiveData<String> isErrorMessageForToast = new MutableLiveData<>();

    protected MutableLiveData<String> isMessageSuccessForToast = new MutableLiveData<>();

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<String> getIsMessageSuccessForToast() {
        return isMessageSuccessForToast;
    }

    public MutableLiveData<String> getIsErrorMessageForToast() {
        return isErrorMessageForToast;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void removeObserver() {

    }

}
