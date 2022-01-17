package com.example.abong.data_local;

import android.content.Context;

import com.example.abong.modle.User;
import com.google.gson.Gson;

public class DataLocalManager {
    private static final String PREF_OBJECT_USER = "PREF_OBJECT_USER";
    private static DataLocalManager instance;
    private MySharePreferences mySharePreferences;

    public static void init(Context context){
        instance = new DataLocalManager();
        instance.mySharePreferences = new MySharePreferences(context);
    }
    public static DataLocalManager getInstance(){
        if (instance == null){
            instance = new DataLocalManager();
        }
        return instance;
    }
    public static void setUser(User user){
        Gson gson = new Gson();
        String jsonUser = gson.toJson(user);
        DataLocalManager.getInstance().mySharePreferences.putValue(PREF_OBJECT_USER,jsonUser);
    }
    public static User getUser(){
        String jsonUser = DataLocalManager.getInstance().mySharePreferences.getValue(PREF_OBJECT_USER);
        Gson gson = new Gson();
        User user = gson.fromJson(jsonUser,User.class);
        return user;
    }
    public static void clearUser(){
        DataLocalManager.getInstance().mySharePreferences.clearValue();
    }
}
