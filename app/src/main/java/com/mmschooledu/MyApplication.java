package com.mmschooledu;

import android.app.Application;

public class MyApplication extends Application {

    AppOpenManager appOpenManager;

    @Override
    public void onCreate() {
        super.onCreate();

        AdController.initAd(this);

        appOpenManager = new AppOpenManager(this);
    }
}
