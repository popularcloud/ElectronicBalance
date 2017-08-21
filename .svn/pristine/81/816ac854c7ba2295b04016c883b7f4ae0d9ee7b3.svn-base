package com.dlc.electronicbalance.activity;

import android.os.Bundle;

import com.dlc.electronicbalance.R;
import com.dlc.electronicbalance.base.BaseActivity;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends BaseActivity {

    @Override
    protected int getLayoutID() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void afterSetContentView(Bundle savedInstanceState) {
        super.afterSetContentView(savedInstanceState);

        Timer timer = new Timer();
        TimerTask task= new TimerTask() {
            @Override
            public void run() {
                startActivity(LoginActivity.class);
                finish();
            }
        };
        timer.schedule(task,2000);
    }
}
