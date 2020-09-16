package com.ydh.botanic.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.ydh.botanic.models.ErrorMessage;
import com.ydh.botanic.models.ResponseModel;
import com.ydh.botanic.models.body.BodyChangePassword;
import com.ydh.botanic.models.usefulClasses.Password;
import com.ydh.botanic.source.remote.responses.ChangePasswordResponse;
import com.ydh.botanic.source.repositories.UserRepository;
import com.ydh.botanic.utils.UserSessionManager;

public class ChangePasswordViewModel extends BaseViewModel {

    public ChangePasswordViewModel(@NonNull Application application) {
        super(application);
    }

    private Password oldPassword;
    private Password newPassword;
    private Password newPassword2;
    private String SUCCESSFULLY_CHANGE_PASSWORD = "Successfully changed password";
    private String SERVICE_OR_CONNECTION_ERROR_PASSWORD = "Failed to change password. Check your connection and try again.";

    private UserRepository repository = new UserRepository(getApplication().getApplicationContext());

    private LiveData<ResponseModel<ChangePasswordResponse>> changePass;
//
//    private MutableLiveData<Boolean> emailContainsErrorStatus = new MutableLiveData<>();
//
//    private MutableLiveData<Boolean> userNameContainsErrorStatus = new MutableLiveData<>();
//
    private MutableLiveData<Boolean> passwordOldContainsErrorStatus = new MutableLiveData<>();

    private MutableLiveData<Boolean> passwordNewContainsErrorStatus = new MutableLiveData<>();

    private MutableLiveData<Boolean> passwordNew2ContainsErrorStatus = new MutableLiveData<>();
//
    private MutableLiveData<String> isChanged = new MutableLiveData<>();
//
//    private MutableLiveData<Boolean> isInvalidEmail = new MutableLiveData<>();
//
//    private MutableLiveData<Boolean> isEmailDuplicated = new MutableLiveData<>();
//
//    private MutableLiveData<Boolean> isInvalidUsername = new MutableLiveData<>();
//
    private MutableLiveData<Boolean> isInvalidPasswordOld = new MutableLiveData<>();

    private MutableLiveData<Boolean> isInvalidPasswordNew2 = new MutableLiveData<>();

    private MutableLiveData<Boolean> isInvalidPasswordNew = new MutableLiveData<>();
//
//    private MutableLiveData<Boolean> isUsernameDuplicated = new MutableLiveData<>();
//
//    private MutableLiveData<Boolean> isMismatchPassword = new MutableLiveData<>();
//
//    public MutableLiveData<Boolean> getIsInvalidEmail() {
//        return isInvalidEmail;
//    }
//
//    public MutableLiveData<Boolean> getIsMismatchPassword() {
//        return isMismatchPassword;
//    }
//
//    public MutableLiveData<Boolean> getIsUsernameDuplicated() {
//        return isUsernameDuplicated;
//    }
//
    public MutableLiveData<Boolean> getIsInvalidPasswordOld() {
        return isInvalidPasswordOld;
    }

    public MutableLiveData<Boolean> getIsInvalidPasswordNew() {
        return isInvalidPasswordNew;
    }

    public MutableLiveData<Boolean> getIsInvalidPasswordNew2() {
        return isInvalidPasswordNew2;
    }

    public MutableLiveData<String> getIsChanged() {
        return isChanged;
    }
//
//    public MutableLiveData<Boolean> getIsInvalidUsername() {
//        return isInvalidUsername;
//    }
//
//    public MutableLiveData<Boolean> getIsEmailDuplicated() {
//        return isEmailDuplicated;
//    }
//
//    public MutableLiveData<Boolean> getEmailContainsErrorStatus() {
//        return emailContainsErrorStatus;
//    }
//
//    public MutableLiveData<Boolean> getUserNameContainsErrorStatus() {
//        return userNameContainsErrorStatus;
//    }
//
    public MutableLiveData<Boolean> getPasswordOldContainsErrorStatus() {
        return passwordOldContainsErrorStatus;
    }

    public MutableLiveData<Boolean> getPasswordNewContainsErrorStatus() {
        return passwordNewContainsErrorStatus;
    }

    public MutableLiveData<Boolean> getPasswordNew2ContainsErrorStatus() {
        return passwordNew2ContainsErrorStatus;
    }
//
//    public void emailTextChanged() {
//        emailContainsErrorStatus.postValue(false);
//    }
//
//    public void userNameTextChanged() {
//        userNameContainsErrorStatus.postValue(false);
//    }
//
    public void passwordOldTextChanged() {
        passwordOldContainsErrorStatus.postValue(false);
    }

    public void passwordNewTextChanged() {
        passwordNewContainsErrorStatus.postValue(false);
    }

    public void passwordNew2TextChanged() {
        passwordNew2ContainsErrorStatus.postValue(false);
    }
//
    public void completedForm(String passwordOldEntered, String passwordNewEntered, String passwordNew2Entered) {

        oldPassword = new Password(passwordOldEntered);
        newPassword = new Password(passwordNewEntered);
        newPassword2 = new Password(passwordNew2Entered);
//        emailContainsErrorStatus.postValue(!email.validateEmail());
//        userNameContainsErrorStatus.postValue(!username.validateUserName());
        passwordOldContainsErrorStatus.postValue(!oldPassword.validatePassword());
        passwordNewContainsErrorStatus.postValue(!newPassword.validatePassword());
        passwordNew2ContainsErrorStatus.postValue(!newPassword2.validatePassword());
        if ( oldPassword.isValidPassword() && newPassword.isValidPassword() && newPassword2.isValidPassword()) {
            BodyChangePassword bodyChangePassword = new BodyChangePassword();

            bodyChangePassword.setOld_password(passwordOldEntered);
            bodyChangePassword.setNew_password(passwordNewEntered);
            bodyChangePassword.setConfirm_new_password(passwordNew2Entered);
            bodyChangePassword.setEmail(UserSessionManager.getLoggedInKeyEmail(getApplication().getApplicationContext()));
            executeServiceRegisterUser(bodyChangePassword);
        }
    }
//
    private Observer<ResponseModel<ChangePasswordResponse>> responseChangePasswordObserver = (ResponseModel<ChangePasswordResponse> responseModel) -> {

        if (responseModel != null) {
            if (responseModel.getCode() == SUCCESS_CODE) {
//                SingletonAccessToken.setAccessTokenReceived(responseModel.getResponse().getToken());
                getIsChanged().setValue(SUCCESSFULLY_CHANGE_PASSWORD);
            } else {

                isLoading.setValue(false);
                ErrorMessage errorMessage = new ErrorMessage();
                errorMessage.setKey(responseModel.getErrorMessage().getKey());
                errorMessage.setError_message(responseModel.getErrorMessage().getError_message());
                System.out.println(errorMessage.getError_message());
//                switch (errorMessage.getKey()) {
//                    case "email.inuse":
//                        getIsEmailDuplicated().setValue(true);
//                        break;
//                    case "error.invalid.username":
//                        getIsInvalidUsername().setValue(true);
//                        break;
//                    case "password.mismatch":
//                        getIsMismatchPassword().setValue(true);
//                        break;
//                    case "username.inuse":
//                        getIsUsernameDuplicated().setValue(true);
//                        break;
//                    default:
//                        getIsErrorMessageForToast().setValue(errorMessage.getError_message());
//                        break;
//                }
            }
        } else {
            isLoading.setValue(false);
            getIsErrorMessageForToast().setValue(SERVICE_OR_CONNECTION_ERROR_PASSWORD);
        }
    };

    private void executeServiceRegisterUser(BodyChangePassword bodyChangePassword) {
        isLoading.setValue(true);
        changePass = repository.postChangePassword(UserSessionManager.getLoggedInKeyToken(getApplication().getApplicationContext()), bodyChangePassword);
        changePass.observeForever(responseChangePasswordObserver);
    }
//
    @Override
    public void removeObserver() {
        super.removeObserver();
        if (changePass != null) {
            changePass.removeObserver(responseChangePasswordObserver);
        }
    }

}
