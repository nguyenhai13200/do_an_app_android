package com.example.abong;

import android.app.Application;

import com.example.abong.data_local.DataLocalManager;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DataLocalManager.init(getApplicationContext());
    }
}
