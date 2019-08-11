package com.sean.android;

import android.content.Context;

import com.sean.base.library.base.BaseApplication;


public class MainApp extends BaseApplication {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getAppContext() {
        return mContext;
    }

}
