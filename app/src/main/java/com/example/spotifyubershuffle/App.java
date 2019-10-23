package com.example.spotifyubershuffle;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

public class App extends Application {
    private static Application sApplication;
    private final Activity mCurrentActivity = null;
    public static Application getApplication() {
        return sApplication;
    }
    public static Context getContext() {
        return getApplication().getApplicationContext();
    }

    public Activity getCurrentActivity(){
        return mCurrentActivity;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
    }
}