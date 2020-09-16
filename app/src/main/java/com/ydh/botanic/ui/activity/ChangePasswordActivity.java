package com.ydh.botanic.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.sdsmdg.tastytoast.TastyToast;
import com.ydh.botanic.R;
import com.ydh.botanic.ui.component.ErrorEditText;
import com.ydh.botanic.viewmodels.ChangePasswordViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChangePasswordActivity extends BaseActivity {

    @BindView(R.id.errorEditText_enterOldPassword)
    ErrorEditText errorEditTextPasswordOld;

    @BindView(R.id.errorEditText_enterNewPassword)
    ErrorEditText errorEditTextPasswordNew;

    @BindView(R.id.errorEditText_enterReNewPassword)
    ErrorEditText errorEditTextPasswordReNew;
//
//    @BindView(R.id.errorEditText_enterPassword2)
//    ErrorEditText errorEditTextPassword2;

    @BindView(R.id.button_nextChagePass)
    Button buttonNextChangePass;

    ChangePasswordViewModel changePasswordViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chage_password);
        ButterKnife.bind(this);
        setupViewModeL();
        setupData();
        setupObservers();

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
//        colorStatusBar(this.getWindow(),R.color.colorBackground,true);
        setupListeners();
    }

    @Override
    public void onBackPressed() {
        finish();

    }


    private void setupData(){

    }

    private void setupObservers() {
//        registerNewUserViewModel.getIsLoading().observe(this, progressBarObserver);
        changePasswordViewModel.getIsChanged().observe(this, ischangedObserver);
        changePasswordViewModel.getPasswordOldContainsErrorStatus().observe(this, passwordOldContainsErrorObserver);
        changePasswordViewModel.getPasswordNewContainsErrorStatus().observe(this, passwordNewContainsErrorObserver);
        changePasswordViewModel.getPasswordNew2ContainsErrorStatus().observe(this, passwordNew2ContainsErrorObserver);
        changePasswordViewModel.getIsInvalidPasswordOld().observe(this,isInvalidPasswordOldObserver);
        changePasswordViewModel.getIsInvalidPasswordNew().observe(this,isInvalidPasswordNewObserver);
        changePasswordViewModel.getIsInvalidPasswordNew2().observe(this,isInvalidPasswordNew2Observer);
//        changePasswordViewModel.get().observe(this,isEmailDuplicated);
//        registerNewUserViewModel.getIsInvalidPassword().observe(this, isInvalidPasswordObserver);
//        registerNewUserViewModel.getIsInvalidPassword2().observe(this, isInvalidPassword2Observer);
//        registerNewUserViewModel.getIsUsernameDuplicated().observe(this,isUsernameDuplicated);
//        registerNewUserViewModel.getIsErrorMessageForToast().observe(this,isErrorMessageForToastObserver);
//        registerNewUserViewModel.getIsMismatchPassword().observe(this, isMismatchPasswordObserver);

    }

    private void setupViewModeL() {
        changePasswordViewModel = ViewModelProviders.of(this).get(ChangePasswordViewModel.class);

    }

    private void setupListeners() {
        errorEditTextPasswordOld.getEditText().addTextChangedListener(editTextPasswordOldTextChangedListener);
        errorEditTextPasswordNew.getEditText().addTextChangedListener(editTextPasswordNewTextChangedListener);
        errorEditTextPasswordReNew.getEditText().addTextChangedListener(editTextPasswordNew2TextChangedListener);
//        errorEditTextPassword2.getEditText().addTextChangedListener(editTextPassword2TextChangedListener);
//        imageViewBackArrow.setOnClickListener(imageViewBackArrowOnClickListener);
        buttonNextChangePass.setOnClickListener(buttonNextChangeOnClickListener);
    }

//    private Observer<Boolean> isUsernameDuplicated = new Observer<Boolean>() {
//        @Override
//        public void onChanged(Boolean isUsernameDuplicated) {
//            if(isUsernameDuplicated){
//                errorEditTextUserName.setMessageError(getString(R.string.duplicate_username));
//                errorEditTextUserName.setErrorVisibility(true);
//            }
//        }
//    };

//    private Observer<Boolean> isEmailDuplicated = new Observer<Boolean>() {
//        @Override
//        public void onChanged(Boolean isUsernameDuplicated) {
//            if(isUsernameDuplicated){
//                errorEditTextEmail.setMessageError(getString(R.string.duplicate_email));
//                errorEditTextEmail.setErrorVisibility(true);
//            }
//        }
//    };

    private Observer<String> isErrorMessageForToastObserver = message -> TastyToast.makeText(getApplicationContext(), message, TastyToast.LENGTH_LONG, TastyToast.ERROR)
            .setGravity(Gravity.CENTER,0,500);

    private Observer<Boolean> isInvalidPasswordNew2Observer = new Observer<Boolean>() {
        @Override
        public void onChanged(Boolean isInvalidPassword) {
            if(isInvalidPassword){
                errorEditTextPasswordReNew.setMessageError(getString(R.string.error_password));
                errorEditTextPasswordReNew.setErrorVisibility(true);
            }
        }
    };

    private Observer<Boolean> isInvalidPasswordOldObserver = new Observer<Boolean>() {
        @Override
        public void onChanged(Boolean isInvalidPassword) {
            if(isInvalidPassword){
                errorEditTextPasswordOld.setMessageError(getString(R.string.error_password));
                errorEditTextPasswordOld.setErrorVisibility(true);
            }
        }
    };

    private Observer<Boolean> isInvalidPasswordNewObserver = new Observer<Boolean>() {
        @Override
        public void onChanged(Boolean isInvalidUsername) {
            if(isInvalidUsername){
                errorEditTextPasswordNew.setMessageError(getString(R.string.error_user_name));
                errorEditTextPasswordReNew.setErrorVisibility(true);
            }
        }
    };

//    private Observer<Boolean> isInvalidEmailObserver = new Observer<Boolean>() {
//        @Override
//        public void onChanged(Boolean isInvalidEmail) {
//            if(isInvalidEmail){
//                errorEditTextEmail.setMessageError(getString(R.string.error_email));
//                errorEditTextEmail.setErrorVisibility(true);
//            }
//        }
//    };

//    private Observer<Boolean> isMismatchPasswordObserver = new Observer<Boolean>() {
//        @Override
//        public void onChanged(Boolean isInvalidEmail) {
//            if(isInvalidEmail){
//                errorEditTextPassword2.setMessageError(getString(R.string.noMatchPassword));
//                errorEditTextPassword2.setErrorVisibility(true);
//            }
//        }
//    };

//    private View.OnClickListener imageViewBackArrowOnClickListener = v -> {
//        int id = v.getId();
//        if (id == R.id.imageView_backArrow) {
//            onBackPressed();
//        }
//    };


    private View.OnClickListener buttonNextChangeOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            hideKeyboardFrom(ChangePasswordActivity.this,
                    errorEditTextPasswordOld);
//            String email = errorEditTextEmail.getText().toString().trim();
//            String username = errorEditTextUserName.getText().toString().trim();
            String passwordOld = errorEditTextPasswordOld.getText().toString().trim();
            String passwordNew = errorEditTextPasswordNew.getText().toString().trim();
            String passwordNew2 = errorEditTextPasswordReNew.getText().toString().trim();
            System.out.println("Pressed button");
            changePasswordViewModel.completedForm(  passwordOld, passwordNew, passwordNew2);
        }
    };

    private Observer<String> ischangedObserver = message -> {
        TastyToast.makeText(getApplicationContext(), message, TastyToast.LENGTH_LONG, TastyToast.SUCCESS)
                .setGravity(Gravity.CENTER,0,500);
        onBackPressed();
//            Intent intent = new Intent(RegisterNewUserActivity.this, MainActivity.class);
//            startActivity(intent);
    };

//    private Observer<Boolean> progressBarObserver = new Observer<Boolean>() {
//        @Override
//        public void onChanged(Boolean isLoading) {
//            loadingExecutor(isLoading,
//                    progressBar,
//                    frameLayout,
//                    buttonNextRegister);
//        }
//    };

//    private Observer<Boolean> emailContainsErrorObserver = new Observer<Boolean>() {
//        @Override
//        public void onChanged(Boolean containsErrorStatus) {
//            if (containsErrorStatus != null) {
//                errorEditTextEmail.setMessageError(getString(R.string.error_email));
//                errorEditTextEmail.setErrorVisibility(containsErrorStatus);
//            }
//        }
//    };
//
//    private Observer<Boolean> usernameContainsErrorObserver = new Observer<Boolean>() {
//        @Override
//        public void onChanged(Boolean containsErrorStatus) {
//            if (containsErrorStatus != null) {
//                errorEditTextUserName.setMessageError(getString(R.string.error_user_name));
//                errorEditTextUserName.setErrorVisibility(containsErrorStatus);
//            }
//        }
//    };

    private Observer<Boolean> passwordOldContainsErrorObserver = new Observer<Boolean>() {
        @Override
        public void onChanged(Boolean containsErrorStatus) {
            if (containsErrorStatus != null) {
                errorEditTextPasswordOld.setMessageError(getString(R.string.error_password));
                errorEditTextPasswordOld.setErrorVisibility(containsErrorStatus);
            }
        }
    };

    private Observer<Boolean> passwordNewContainsErrorObserver = new Observer<Boolean>() {
        @Override
        public void onChanged(Boolean containsErrorStatus) {
            if (containsErrorStatus != null) {
                errorEditTextPasswordNew.setMessageError(getString(R.string.error_password));
                errorEditTextPasswordNew.setErrorVisibility(containsErrorStatus);
            }
        }
    };

    private Observer<Boolean> passwordNew2ContainsErrorObserver = new Observer<Boolean>() {
        @Override
        public void onChanged(Boolean containsErrorStatus) {
            if (containsErrorStatus != null) {
                errorEditTextPasswordReNew.setMessageError(getString(R.string.error_password));
                errorEditTextPasswordReNew.setErrorVisibility(containsErrorStatus);
            }
        }
    };

//    private TextWatcher editTextUserNameTextChangedListener = new TextWatcher() {
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//        }
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//            registerNewUserViewModel.userNameTextChanged();
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//
//        }
//    };

    private TextWatcher editTextPasswordOldTextChangedListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            changePasswordViewModel.passwordOldTextChanged();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private TextWatcher editTextPasswordNewTextChangedListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            changePasswordViewModel.passwordNewTextChanged();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private TextWatcher editTextPasswordNew2TextChangedListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            changePasswordViewModel.passwordNew2TextChanged();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        changePasswordViewModel.removeObserver();
    }
}
