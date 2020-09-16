package com.ydh.botanic.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.ydh.botanic.models.ErrorMessage;
import com.ydh.botanic.models.ResponseModel;
import com.ydh.botanic.models.body.BodyRegistration;
import com.ydh.botanic.models.usefulClasses.Email;
import com.ydh.botanic.models.usefulClasses.Password;
import com.ydh.botanic.models.usefulClasses.Username;
import com.ydh.botanic.source.remote.responses.RegistrationResponse;
import com.ydh.botanic.source.repositories.UserRepository;

public class RegisterNewUserViewModel extends BaseViewModel {

    private Email email;
    private Password password;
    private Password password2;
    private Username username;
    private String SUCCESSFULLY_REGISTERED = "Successfully registered. Please confirm your email address to login";
    private String SERVICE_OR_CONNECTION_ERROR_REGISTER = "Failed to register. Check your connection and try again.";

    private UserRepository repository = new UserRepository(getApplication().getApplicationContext());

    private LiveData<ResponseModel<RegistrationResponse>> registerUser;

    private MutableLiveData<Boolean> emailContainsErrorStatus = new MutableLiveData<>();

    private MutableLiveData<Boolean> userNameContainsErrorStatus = new MutableLiveData<>();

    private MutableLiveData<Boolean> passwordContainsErrorStatus = new MutableLiveData<>();

    private MutableLiveData<Boolean> password2ContainsErrorStatus = new MutableLiveData<>();

    private MutableLiveData<String> isRegistered = new MutableLiveData<>();

    private MutableLiveData<Boolean> isInvalidEmail = new MutableLiveData<>();

    private MutableLiveData<Boolean> isEmailDuplicated = new MutableLiveData<>();

    private MutableLiveData<Boolean> isInvalidUsername = new MutableLiveData<>();

    private MutableLiveData<Boolean> isInvalidPassword = new MutableLiveData<>();

    private MutableLiveData<Boolean> isInvalidPassword2 = new MutableLiveData<>();

    private MutableLiveData<Boolean> isUsernameDuplicated = new MutableLiveData<>();

    private MutableLiveData<Boolean> isMismatchPassword = new MutableLiveData<>();

    public RegisterNewUserViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Boolean> getIsInvalidEmail() {
        return isInvalidEmail;
    }

    public MutableLiveData<Boolean> getIsMismatchPassword() {
        return isMismatchPassword;
    }

    public MutableLiveData<Boolean> getIsUsernameDuplicated() {
        return isUsernameDuplicated;
    }

    public MutableLiveData<Boolean> getIsInvalidPassword() {
        return isInvalidPassword;
    }

    public MutableLiveData<Boolean> getIsInvalidPassword2() {
        return isInvalidPassword2;
    }

    public MutableLiveData<String> getIsRegistered() {
        return isRegistered;
    }

    public MutableLiveData<Boolean> getIsInvalidUsername() {
        return isInvalidUsername;
    }

    public MutableLiveData<Boolean> getIsEmailDuplicated() {
        return isEmailDuplicated;
    }

    public MutableLiveData<Boolean> getEmailContainsErrorStatus() {
        return emailContainsErrorStatus;
    }

    public MutableLiveData<Boolean> getUserNameContainsErrorStatus() {
        return userNameContainsErrorStatus;
    }

    public MutableLiveData<Boolean> getPasswordContainsErrorStatus() {
        return passwordContainsErrorStatus;
    }

    public MutableLiveData<Boolean> getPassword2ContainsErrorStatus() {
        return password2ContainsErrorStatus;
    }

    public void emailTextChanged() {
        emailContainsErrorStatus.postValue(false);
    }

    public void userNameTextChanged() {
        userNameContainsErrorStatus.postValue(false);
    }

    public void passwordTextChanged() {
        passwordContainsErrorStatus.postValue(false);
    }

    public void password2TextChanged() {
        password2ContainsErrorStatus.postValue(false);
    }

    public void completedForm(String emailEntered, String usernameEntered, String passwordEntered, String password2Entered) {
        email = new Email(emailEntered);
        password = new Password(passwordEntered);
        password2 = new Password(password2Entered);
        username = new Username(usernameEntered);
        emailContainsErrorStatus.postValue(!email.validateEmail());
        userNameContainsErrorStatus.postValue(!username.validateUserName());
        passwordContainsErrorStatus.postValue(!password.validatePassword());
        password2ContainsErrorStatus.postValue(!password2.validatePassword());
        if (email.isValidEmail() && username.isValidUserName() && password.isValidPassword() && password2.isValidPassword()) {
            BodyRegistration bodyRegistration = new BodyRegistration();

            bodyRegistration.setEmail(emailEntered);
            bodyRegistration.setUsername(usernameEntered);
            bodyRegistration.setPassword(passwordEntered);
            bodyRegistration.setPassword2(password2Entered);
            executeServiceRegisterUser(bodyRegistration);
        }
    }

    private Observer<ResponseModel<RegistrationResponse>> responseRegisterUserObserver = (ResponseModel<RegistrationResponse> responseModel) -> {

        if (responseModel != null) {
            if (responseModel.getCode() == SUCCESS_CODE) {
//                SingletonAccessToken.setAccessTokenReceived(responseModel.getResponse().getToken());
                getIsRegistered().setValue(SUCCESSFULLY_REGISTERED);
            } else {

                isLoading.setValue(false);
                ErrorMessage errorMessage = new ErrorMessage();
                errorMessage.setKey(responseModel.getErrorMessage().getKey());
                errorMessage.setError_message(responseModel.getErrorMessage().getError_message());
                System.out.println(errorMessage.getError_message());
                switch (errorMessage.getKey()) {
                    case "email.inuse":
                        getIsEmailDuplicated().setValue(true);
                        break;
                    case "error.invalid.username":
                        getIsInvalidUsername().setValue(true);
                        break;
                    case "password.mismatch":
                        getIsMismatchPassword().setValue(true);
                        break;
                    case "username.inuse":
                        getIsUsernameDuplicated().setValue(true);
                        break;
                    default:
                        getIsErrorMessageForToast().setValue(errorMessage.getError_message());
                        break;
                }
            }
        } else {
            isLoading.setValue(false);
            getIsErrorMessageForToast().setValue(SERVICE_OR_CONNECTION_ERROR_REGISTER);
        }
    };

    private void executeServiceRegisterUser(BodyRegistration bodyRegistration) {
        isLoading.setValue(true);
        registerUser = repository.postUserRegister(bodyRegistration);
        registerUser.observeForever(responseRegisterUserObserver);
    }

    @Override
    public void removeObserver() {
        super.removeObserver();
        if (registerUser != null) {
            registerUser.removeObserver(responseRegisterUserObserver);
        }
    }

}
