package com.ydh.botanic.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.ydh.botanic.R;
import com.ydh.botanic.utils.FirstRun;
import com.ydh.botanic.utils.UserSessionManager;

public class SplashScreenActivity  extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            checkFirstRun();
            finish();
        }, 2000);

    }
    private void checkFirstRun() {
        if (FirstRun.getFirstRun(getApplicationContext()) || !UserSessionManager.getLoggedInStatus(getApplicationContext())) {
            //show sign up activity
            startActivity(new Intent(SplashScreenActivity.this, LoginUserActivity.class));
            FirstRun.setFirstRun(getApplicationContext(), false);
        }else {
            Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}
