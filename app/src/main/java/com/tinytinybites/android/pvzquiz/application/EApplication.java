package com.tinytinybites.android.pvzquiz.application;

import android.app.Application;

/**
 * Created by bundee on 9/14/16.
 */
public class EApplication extends Application {

    protected static EApplication _instance;

    @Override
    public void onCreate() {
        super.onCreate();

        _instance = this;
    }

    /**
     * Get application instance
     */
    public static EApplication getInstance(){
        //Note: Application class started when application starts, _instance should never be null.
        return _instance;
    }

}
