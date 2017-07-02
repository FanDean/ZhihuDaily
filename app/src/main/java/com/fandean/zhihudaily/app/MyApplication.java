package com.fandean.zhihudaily.app;

import android.app.Application;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatDelegate;

import static com.fandean.zhihudaily.ui.MainActivity.NIGHT_PREFERENCE_FILE_NAME;
import static com.fandean.zhihudaily.ui.MainActivity.NIGHT_PREFERENCE_KEY;

/**
 * Created by fan on 17-7-2.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences preferences = getSharedPreferences(NIGHT_PREFERENCE_FILE_NAME,MODE_PRIVATE);
        Boolean isNightTheme = preferences.getBoolean(NIGHT_PREFERENCE_KEY,false);
        if (isNightTheme){
            //设置夜间模式，这里的逻辑需与MainActivity中的setTheme()方法中的相反
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}
