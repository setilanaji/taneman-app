package com.ydh.botanic.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.ydh.botanic.models.ResponseModel;
import com.ydh.botanic.models.body.BodyLogin;
import com.ydh.botanic.models.usefulClasses.Email;
import com.ydh.botanic.models.usefulClasses.Password;
import com.ydh.botanic.source.remote.responses.LoginResponse;
import com.ydh.botanic.source.repositories.UserRepository;


public class LoginUserViewModel extends BaseViewModel {

    private Password password;
    private Email email;
    private String KEY_INVALID_PASSWORD = "error.invalid.password";
    private String KEY_UNAUTHORIZED_LOGIN =  "error.unauthorized.login";
    private String KEY_UNAUTHORIZED_PASSWORD = "error.unauthorized.password";
    private String SUCCESS_MESSAGE_LOGIN = "Kata sandi dikonfirmasi, login resmi!";
    private String FAILED_MESSAGE_LOGIN = "Kata sandi dikonfirmasi, login resmi!";
    private String SERVICE_OR_CONNECTION_ERROR_LOGIN = "Gagal memvalidasi kata sandi Anda. Periksa koneksi dan coba lagi.";

    public enum RegisteredState {
        REGISTERED,
        PREREGISTERED,
        UNREGISTERED
    }

//    SharedPreferences sharedpreferences = UserSessionManager.get("preference_key", Context.MODE_PRIVATE);

    private UserRepository repository = new UserRepository(getApplication().getApplicationContext());

    final MutableLiveData<RegisteredState> registeredStateMutableLiveData =
            new MutableLiveData<>();

    public MutableLiveData<RegisteredState> getRegisteredStateMutableLiveData() {
        return registeredStateMutableLiveData;
    }

    private LiveData<ResponseModel<LoginResponse>> login;

    private MutableLiveData<Boolean> emailContainsErrorStatus = new MutableLiveData<>();

    private MutableLiveData<Boolean> passwordContainsErrorStatus = new MutableLiveData<>();

    private MutableLiveData<String> messageErrorChanged = new MutableLiveData<>();

    private MutableLiveData<String> isLogged = new MutableLiveData<>();

    private MutableLiveData<Boolean> isInvalidEmail = new MutableLiveData<>();

    private MutableLiveData<Boolean> isInvalidPassword = new MutableLiveData<>();

    public LoginUserViewModel(@NonNull Application application) {
        super(application);
        registeredStateMutableLiveData.setValue(RegisteredState.UNREGISTERED);
    }


    public MutableLiveData<String> getIsLogged() {
        return isLogged;
    }

    public MutableLiveData<Boolean> getIsInvalidEmail() {
        return isInvalidEmail;
    }


    public MutableLiveData<Boolean> getIsInvalidPassword() {
        return isInvalidPassword;
    }

    public MutableLiveData<String> getMessageErrorChanged() {
        return messageErrorChanged;
    }


    public MutableLiveData<Boolean> getEmailContainsErrorStatus() {
        return emailContainsErrorStatus;
    }

    public MutableLiveData<Boolean> getPasswordContainsErrorStatus() {
        return passwordContainsErrorStatus;
    }

    public void emailTextChanged() {
        emailContainsErrorStatus.postValue(false);
    }

    public void passwordTextChanged(){
        passwordContainsErrorStatus.postValue(false);
    }

    public void completedForm(String passwordEntered, String emailEntered){
        email = new Email(emailEntered);
        password = new Password(passwordEntered);
        emailContainsErrorStatus.postValue(!email.validateEmail());
        passwordContainsErrorStatus.postValue(!password.validatePassword());
        if (password.isValidPassword() && email.isValidEmail()) {
            BodyLogin bodyLogin = new BodyLogin();
            bodyLogin.setUsername(emailEntered);
            bodyLogin.setPassword(passwordEntered);
            System.out.println(bodyLogin.getUsername() + bodyLogin.getPassword());
            executeServiceLoginUser(bodyLogin);
        }
    }

//    public void changeSuccessMessageResendToken(){
//        SUCCESS_RESEND_TOKEN = "Foi enviado um código para seu e-mail!";
//    }
//
//    @Override
//    public void tokenForwardingRequested() {
//        super.tokenForwardingRequested();
//        passwordContainsErrorStatus.setValue(false);
//        changeSuccessMessageResendToken();
//        String email = SingletonEmail.INSTANCE.getEmail();
//        executeServiceTokenResend(email);
//    }



    public boolean isMessageErrorTopToast(String key){
        return (key.equals(KEY_INVALID_PASSWORD)
                || key.equals(KEY_UNAUTHORIZED_LOGIN)
                || key.equals(KEY_UNAUTHORIZED_PASSWORD));
    }

    private Observer<ResponseModel<LoginResponse>> responseLoginObserver = new Observer<ResponseModel<LoginResponse>>() {
        @Override
        public void onChanged(ResponseModel<LoginResponse> responseModel) {
            if (responseModel != null) {
                if (responseModel.getCode() == SUCCESS_CODE) {
                    if (responseModel.getResponse().getActive()){
                        LoginUserViewModel.this.getIsLogged().setValue(SUCCESS_MESSAGE_LOGIN);
                        registeredStateMutableLiveData.setValue(RegisteredState.REGISTERED);

                    } else {
                        LoginUserViewModel.this.getIsLogged().setValue(FAILED_MESSAGE_LOGIN);
                        registeredStateMutableLiveData.setValue(RegisteredState.PREREGISTERED);
                    }
                } else {
                    isLoading.setValue(false);
                    String key = responseModel.getErrorMessage().getKey();
                    String message = responseModel.getErrorMessage().getError_message();
                    if (LoginUserViewModel.this.isMessageErrorTopToast(key)) {
                        LoginUserViewModel.this.getMessageErrorChanged().setValue(message);
                    } else {
                        LoginUserViewModel.this.getIsErrorMessageForToast().setValue(message);
                    }
                }
            } else {
                isLoading.setValue(false);
                LoginUserViewModel.this.getIsErrorMessageForToast().setValue(SERVICE_OR_CONNECTION_ERROR_LOGIN);
            }
        }
    };

    private void executeServiceLoginUser(BodyLogin bodyLogin) {
        isLoading.setValue(true);
        login = repository.postUserLogin(bodyLogin);
        login.observeForever(responseLoginObserver);
    }

    @Override
    public void removeObserver() {
        super.removeObserver();
        if (login != null) {
            login.removeObserver(responseLoginObserver);
        }
    }
}
