package com.moyuchen.hook;

import android.app.Application;
import android.util.Log;

import com.moyuchen.hook.hookStartActivity.HookStartActivityUtil;

/**
 * @Author zhangyabo
 * @Date 2020-04-19
 * @Des
 **/
public class MyApplication extends Application {
    private String TAG = getClass().getSimpleName();
    @Override
    public void onCreate() {
        super.onCreate();

    }
}
