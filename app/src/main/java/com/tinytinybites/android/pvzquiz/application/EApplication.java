package com.tinytinybites.android.pvzquiz.application;

import android.app.Application;

import com.tinytinybites.android.pvzquiz.data.DataManager;

/**
 * Created by bundee on 9/14/16.
 */
public class EApplication extends Application {

    protected static EApplication _instance;
    private DataManager mQuizDataManager;

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

    public DataManager getDataManager(){
        if(mQuizDataManager == null){
            mQuizDataManager = new DataManager();
        }
        return mQuizDataManager;
    }

}
