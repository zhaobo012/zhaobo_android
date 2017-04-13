package com.zhaobo.moudledevpro.application;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

import com.zhaobo.dbmoudle.DBHelper;

import java.io.File;


/**
 * Created by zhaobo on 17/4/12.
 */

public class MyApplication extends Application {

    private static final String TAG = "MyApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        File cacheFile=getExternalCacheDir();
        if(cacheFile.exists())
            Log.i(TAG,cacheFile.getAbsolutePath());
        else
            Log.i(TAG,"noFile");
        DBHelper.getInstance().initOpenHelper(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        // 程序终止的时候执行
        Log.d(TAG, "onTerminate");
        super.onTerminate();
        DBHelper.getInstance().closeDbConnections();
    }
}
