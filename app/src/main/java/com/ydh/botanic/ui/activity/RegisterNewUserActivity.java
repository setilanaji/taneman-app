package com.ydh.botanic.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.sdsmdg.tastytoast.TastyToast;
import com.ydh.botanic.R;
import com.ydh.botanic.ui.component.ErrorEditText;
import com.ydh.botanic.viewmodels.RegisterNewUserViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterNewUserActivity extends BaseActivity {

    @BindView(R.id.errorEditText_enterEmail)
    ErrorEditText errorEditTextEmail;

    @BindView(R.id.errorEditText_enterUserName)
    ErrorEditText errorEditTextUserName;

    @BindView(R.id.errorEditText_enterPassword)
    ErrorEditText errorEditTextPassword;

    @BindView(R.id.errorEditText_enterPassword2)
    ErrorEditText errorEditTextPassword2;

    @BindView(R.id.button_nextChagePass)
    Button buttonNextRegister;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.loading_layout)
    FrameLayout frameLayout;

    @BindView(R.id.imageView_backArrow)
    ImageView imageViewBackArrow;

    RegisterNewUserViewModel registerNewUserViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_user);
        ButterKnife.bind(this);
        setupViewModeL();
        setupData();
        setupObservers();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        colorStatusBar(this.getWindow(),R.color.colorBackground,true);
        setupListeners();
    }

    private void setupObservers() {
        registerNewUserViewModel.getIsLoading().observe(this, progressBarObserver);
        registerNewUserViewModel.getUserNameContainsErrorStatus().observe(this, usernameContainsErrorObserver);
        registerNewUserViewModel.getEmailContainsErrorStatus().observe(this, emailContainsErrorObserver);
        registerNewUserViewModel.getPasswordContainsErrorStatus().observe(this, passwordContainsErrorObserver);
        registerNewUserViewModel.getPassword2ContainsErrorStatus().observe(this, password2ContainsErrorObserver);
        registerNewUserViewModel.getIsRegistered().observe(this,isRegisteredObserver);
        registerNewUserViewModel.getIsInvalidUsername().observe(this,isInvalidUsernameObserver);
        registerNewUserViewModel.getIsInvalidEmail().observe(this,isInvalidEmailObserver);
        registerNewUserViewModel.getIsEmailDuplicated().observe(this,isEmailDuplicated);
        registerNewUserViewModel.getIsInvalidPassword().observe(this, isInvalidPasswordObserver);
        registerNewUserViewModel.getIsInvalidPassword2().observe(this, isInvalidPassword2Observer);
        registerNewUserViewModel.getIsUsernameDuplicated().observe(this,isUsernameDuplicated);
        registerNewUserViewModel.getIsErrorMessageForToast().observe(this,isErrorMessageForToastObserver);
        registerNewUserViewModel.getIsMismatchPassword().observe(this, isMismatchPasswordObserver);

    }

    private void setupViewModeL() {
        registerNewUserViewModel = ViewModelProviders.of(this).get(RegisterNewUserViewModel.class);

    }

    private void setupListeners() {
        errorEditTextEmail.getEditText().addTextChangedListener(editTextEmailTextChangedListener);
        errorEditTextUserName.getEditText().addTextChangedListener(editTextUserNameTextChangedListener);
        errorEditTextPassword.getEditText().addTextChangedListener(editTextPasswordTextChangedListener);
        errorEditTextPassword2.getEditText().addTextChangedListener(editTextPassword2TextChangedListener);
        imageViewBackArrow.setOnClickListener(imageViewBackArrowOnClickListener);
        buttonNextRegister.setOnClickListener(buttonNextRegisterOnClickListener);
    }

    private Observer<Boolean> isUsernameDuplicated = new Observer<Boolean>() {
        @Override
        public void onChanged(Boolean isUsernameDuplicated) {
            if(isUsernameDuplicated){
                errorEditTextUserName.setMessageError(getString(R.string.duplicate_username));
                errorEditTextUserName.setErrorVisibility(true);
            }
        }
    };

    private Observer<Boolean> isEmailDuplicated = new Observer<Boolean>() {
        @Override
        public void onChanged(Boolean isUsernameDuplicated) {
            if(isUsernameDuplicated){
                errorEditTextEmail.setMessageError(getString(R.string.duplicate_email));
                errorEditTextEmail.setErrorVisibility(true);
            }
        }
    };

    private Observer<String> isErrorMessageForToastObserver = message -> TastyToast.makeText(getApplicationContext(), message, TastyToast.LENGTH_LONG, TastyToast.ERROR)
            .setGravity(Gravity.CENTER,0,500);

    private Observer<Boolean> isInvalidPasswordObserver = new Observer<Boolean>() {
        @Override
        public void onChanged(Boolean isInvalidPassword) {
            if(isInvalidPassword){
                errorEditTextPassword.setMessageError(getString(R.string.error_password));
                errorEditTextPassword.setErrorVisibility(true);
            }
        }
    };

    private Observer<Boolean> isInvalidPassword2Observer = new Observer<Boolean>() {
        @Override
        public void onChanged(Boolean isInvalidPassword) {
            if(isInvalidPassword){
                errorEditTextPassword2.setMessageError(getString(R.string.error_password));
                errorEditTextPassword2.setErrorVisibility(true);
            }
        }
    };

    private Observer<Boolean> isInvalidUsernameObserver = new Observer<Boolean>() {
        @Override
        public void onChanged(Boolean isInvalidUsername) {
            if(isInvalidUsername){
                errorEditTextUserName.setMessageError(getString(R.string.error_user_name));
                errorEditTextUserName.setErrorVisibility(true);
            }
        }
    };

    private Observer<Boolean> isInvalidEmailObserver = new Observer<Boolean>() {
        @Override
        public void onChanged(Boolean isInvalidEmail) {
            if(isInvalidEmail){
                errorEditTextEmail.setMessageError(getString(R.string.error_email));
                errorEditTextEmail.setErrorVisibility(true);
            }
        }
    };

    private Observer<Boolean> isMismatchPasswordObserver = new Observer<Boolean>() {
        @Override
        public void onChanged(Boolean isInvalidEmail) {
            if(isInvalidEmail){
                errorEditTextPassword2.setMessageError(getString(R.string.noMatchPassword));
                errorEditTextPassword2.setErrorVisibility(true);
            }
        }
    };

    private View.OnClickListener imageViewBackArrowOnClickListener = v -> {
        int id = v.getId();
        if (id == R.id.imageView_backArrow) {
            onBackPressed();
        }
    };


    private View.OnClickListener buttonNextRegisterOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            hideKeyboardFrom(RegisterNewUserActivity.this,
                    errorEditTextUserName);
            String email = errorEditTextEmail.getText().toString().trim();
            String username = errorEditTextUserName.getText().toString().trim();
            String password = errorEditTextPassword.getText().toString().trim();
            String password2 = errorEditTextPassword2.getText().toString().trim();
            registerNewUserViewModel.completedForm(email, username, password, password2);
        }
    };

    private Observer<String> isRegisteredObserver = message -> {
        TastyToast.makeText(getApplicationContext(), message, TastyToast.LENGTH_LONG, TastyToast.SUCCESS)
                .setGravity(Gravity.CENTER,0,500);
        onBackPressed();
//            Intent intent = new Intent(RegisterNewUserActivity.this, MainActivity.class);
//            startActivity(intent);
    };

    private Observer<Boolean> progressBarObserver = new Observer<Boolean>() {
        @Override
        public void onChanged(Boolean isLoading) {
            loadingExecutor(isLoading,
                    progressBar,
                    frameLayout,
                    buttonNextRegister);
        }
    };

    private Observer<Boolean> emailContainsErrorObserver = new Observer<Boolean>() {
        @Override
        public void onChanged(Boolean containsErrorStatus) {
            if (containsErrorStatus != null) {
                errorEditTextEmail.setMessageError(getString(R.string.error_email));
                errorEditTextEmail.setErrorVisibility(containsErrorStatus);
            }
        }
    };

    private Observer<Boolean> usernameContainsErrorObserver = new Observer<Boolean>() {
        @Override
        public void onChanged(Boolean containsErrorStatus) {
            if (containsErrorStatus != null) {
                errorEditTextUserName.setMessageError(getString(R.string.error_user_name));
                errorEditTextUserName.setErrorVisibility(containsErrorStatus);
            }
        }
    };

    private Observer<Boolean> passwordContainsErrorObserver = new Observer<Boolean>() {
        @Override
        public void onChanged(Boolean containsErrorStatus) {
            if (containsErrorStatus != null) {
                errorEditTextPassword.setMessageError(getString(R.string.error_password));
                errorEditTextPassword.setErrorVisibility(containsErrorStatus);
            }
        }
    };

    private Observer<Boolean> password2ContainsErrorObserver = new Observer<Boolean>() {
        @Override
        public void onChanged(Boolean containsErrorStatus) {
            if (containsErrorStatus != null) {
                errorEditTextPassword2.setMessageError(getString(R.string.error_password));
                errorEditTextPassword2.setErrorVisibility(containsErrorStatus);
            }
        }
    };

    private TextWatcher editTextUserNameTextChangedListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            registerNewUserViewModel.userNameTextChanged();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private TextWatcher editTextPasswordTextChangedListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            registerNewUserViewModel.passwordTextChanged();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private TextWatcher editTextEmailTextChangedListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            registerNewUserViewModel.emailTextChanged();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private TextWatcher editTextPassword2TextChangedListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            registerNewUserViewModel.password2TextChanged();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void onBackPressed() {
        finish();

    }

    private void setupData(){

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        registerNewUserViewModel.removeObserver();
    }
}
