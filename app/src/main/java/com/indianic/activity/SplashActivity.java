package com.indianic.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.indianic.R;


/**
 * Simple startup activity showing static image.
 */
public class SplashActivity extends BaseActivity {

    /*
     * Handler is used to set some delay on this screen
     */
    private Handler handler;

    @Override
    protected int defineLayoutResource() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isDuplicateInstance()) {//return if this is duplicate instance of same category and instance
            return;
        }
        executeHandler();
    }

    private void executeHandler() {
        handler = new Handler();
        long INTERVAL = 2000;
        handler.postDelayed(runnable, INTERVAL);
    }

    @Override
    protected void initializeComponents() {
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            final Intent homeIntent = new Intent(SplashActivity.this, HomeActivity.class);
            startActivity(homeIntent);
            finish();
        }
    };

    /**
     * This method will prevent multiple instances of an activity when it is launched with different intents
     */
    private boolean isDuplicateInstance() {
        if (!isTaskRoot()) {
            final Intent intent = getIntent();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(intent.getAction())) {
                finish();
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacks(runnable);
            finish();
        }
    }
}
