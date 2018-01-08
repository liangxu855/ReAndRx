package com.example.administrator.reandrx;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2018-01-08.
 */

public class MyApplication extends Application {

    private static Context context; //全局上下文
    public NetWorkType netWorkType = NetWorkType.NULL;
    public static String appCachePath;
    public static String appFilePath;
    public static String appSDCachePath;
    public static String appSDFilePath;

    public enum NetWorkType {  //用户联网状态
        WIFI, ETHERNET, MOBILE, NetWorkType, NULL
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();  //初始化全局上下文

        appCachePath = getCacheDir().getPath();
        appFilePath = getFilesDir().getPath();
//        appSDCachePath = Environment.getExternalStorageDirectory().getPath() + "/" + getResources().getString(R.string.app_name_letter) + "/cache";
//        appSDFilePath = Environment.getExternalStorageDirectory().getPath() + "/" + getResources().getString(R.string.app_name_letter) + "/file";
    }

    public static Context getContext() {
        return context;
    }
}
