package com.example.abong.data_local;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharePreferences {
    private static final String MY_SHARED_PREFERENCES = "MY_SHARED_PREFERENCES";
    private Context context;
    public MySharePreferences(Context context){
        this.context = context;
    }
    public void putValue(String key, String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_PREFERENCES,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
    public String getValue(String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_PREFERENCES,Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }
    public void clearValue(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_PREFERENCES,Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }
}
