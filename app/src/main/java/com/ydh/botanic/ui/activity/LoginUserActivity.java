package com.ydh.botanic.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.sdsmdg.tastytoast.TastyToast;
import com.ydh.botanic.R;
import com.ydh.botanic.ui.component.ErrorEditText;
import com.ydh.botanic.viewmodels.LoginUserViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginUserActivity  extends BaseActivity {

    @BindView(R.id.textView_register)
    TextView toRegister;

    @BindView(R.id.errorEditText_login_enterPassword)
    ErrorEditText errorEditTextPassword;

    @BindView(R.id.errorEditText_login_enterEmail)
    ErrorEditText errorEditTextEmail;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.button_sign_in)
    Button buttonSignIn;

    @BindView(R.id.loading_layout)
    FrameLayout frameLayout;

    @BindView(R.id.linearLayout_red_toast)
    LinearLayout linearLayout;

    @BindView(R.id.textView_red_toast)
    TextView textViewRedToast;

    @BindView(R.id.layout_login)
    ConstraintLayout constraintLayout;

    @BindView(R.id.textView_password)
    TextView resetPassword;

    LoginUserViewModel loginUserViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        View view = this.getLayoutInflater().inflate(R.layout.activity_register_new_user, null);
        setContentView(R.layout.activity_login_user);
        ButterKnife.bind(this);
        setupViewModeL();
        setupData();
        setupObservers();
    }

    private void setupData() {
    }

    private void setupObservers() {
        loginUserViewModel.getIsLoading().observe(this, progressBarObserver);
        loginUserViewModel.getRegisteredStateMutableLiveData().observe(this, isLoggedObserver);
        loginUserViewModel.getEmailContainsErrorStatus().observe(this, emailContainsErrorObserver);
        loginUserViewModel.getPasswordContainsErrorStatus().observe(this, passwordContainsErrorObserver);
        loginUserViewModel.getIsInvalidEmail().observe(this,isInvalidEmailObserver);
        loginUserViewModel.getIsInvalidPassword().observe(this, isInvalidPasswordObserver);
        loginUserViewModel.getIsErrorMessageForToast().observe(this,isErrorMessageForToastObserver);


    }
        private void setupViewModeL() {
        loginUserViewModel = ViewModelProviders.of(this).get(LoginUserViewModel.class);

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
//        colorStatusBar(this.getWindow(),R.color.colorBackground,true);
        setupListeners();
    }

    private void setupListeners() {
        toRegister.setOnClickListener(toRegisterListener);
        errorEditTextEmail.getEditText().addTextChangedListener(editTextEmailTextChangedListener);
        errorEditTextPassword.getEditText().addTextChangedListener(editTextPasswordTextChangedListener);
        buttonSignIn.setOnClickListener(buttonSignInOnClickListener);
        resetPassword.setOnClickListener(resetPasswordListener);
//        textViewForgetPassword.setOnClickListener(textViewForgetPasswordOnClickListener);


    }

//    private View.OnClickListener textViewForgetPasswordOnClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            loginViewModel.tokenForwardingRequested();
//        }
//    };

    private View.OnClickListener buttonSignInOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            hideKeyboardFrom(LoginUserActivity.this,
                    errorEditTextPassword);
            String password = errorEditTextPassword.getText().toString().trim();
            String email = errorEditTextEmail.getText().toString().trim();
            System.out.println(email);
            loginUserViewModel.completedForm(password, email);
        }
    };

    private View.OnClickListener resetPasswordListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://192.168.43.243:8000/reset-password/")));
        }
    };


    private TextWatcher editTextPasswordTextChangedListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            loginUserViewModel.passwordTextChanged();
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
            loginUserViewModel.emailTextChanged();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private View.OnClickListener toRegisterListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.textView_register) {
                Intent intent = new Intent(LoginUserActivity.this, RegisterNewUserActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
    };

    private Observer<Boolean> emailContainsErrorObserver = new Observer<Boolean>() {
        @Override
        public void onChanged(Boolean containsErrorStatus) {
            if (containsErrorStatus != null) {
                errorEditTextEmail.setErrorVisibility(containsErrorStatus);
            }
        }
    };

    private Observer<String> isErrorMessageForToastObserver = new Observer<String>() {
        @Override
        public void onChanged(String message) {
            TastyToast.makeText(getApplicationContext(), message, TastyToast.LENGTH_LONG, TastyToast.ERROR)
                    .setGravity(Gravity.CENTER,0,500);
        }
    };

    private Observer<Boolean> passwordContainsErrorObserver = new Observer<Boolean>() {
        @Override
        public void onChanged(Boolean containsErrorStatus) {
            if (containsErrorStatus != null) {
                errorEditTextPassword.setErrorVisibility(containsErrorStatus);
            }
        }
    };

    private Observer<Boolean> isInvalidPasswordObserver = new Observer<Boolean>() {
        @Override
        public void onChanged(Boolean isInvalidPassword) {
            if(isInvalidPassword){
                errorEditTextPassword.setErrorVisibility(true);
            }
        }
    };

    private Observer<Boolean> isInvalidEmailObserver = new Observer<Boolean>() {
        @Override
        public void onChanged(Boolean isInvalidEmail) {
            if(isInvalidEmail){
                errorEditTextEmail.setErrorVisibility(true);
            }
        }
    };

    private Observer<Boolean> progressBarObserver = new Observer<Boolean>() {
        @Override
        public void onChanged(Boolean isLoading) {
            loadingExecutor(isLoading,
                    progressBar,
                    frameLayout,
                    buttonSignIn);
        }
    };

    private Observer<LoginUserViewModel.RegisteredState> isLoggedObserver = new Observer<LoginUserViewModel.RegisteredState>(){
        @Override
        public void onChanged(LoginUserViewModel.RegisteredState registeredState) {
            switch (registeredState){
                case REGISTERED:
                    TastyToast.makeText(getApplicationContext(), loginUserViewModel.getIsLogged().getValue(), TastyToast.LENGTH_LONG, TastyToast.SUCCESS)
                            .setGravity(Gravity.CENTER,0,500);
                    Intent intent = new Intent(LoginUserActivity.this, MainActivity.class);
                    startActivity(intent);
                    break;
                case PREREGISTERED:
                    TastyToast.makeText(getApplicationContext(), loginUserViewModel.getIsLogged().getValue(), TastyToast.LENGTH_LONG, TastyToast.INFO)
                            .setGravity(Gravity.CENTER,0,500);
                    break;
                case UNREGISTERED:
                    break;
            }
        }

    };

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginUserViewModel.removeObserver();
    }
}
