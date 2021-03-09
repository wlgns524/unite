package com.rightcode.unite;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.multidex.MultiDexApplication;

import com.bumptech.glide.Glide;
import com.google.firebase.FirebaseApp;
import com.kakao.sdk.common.KakaoSdk;
import com.rightcode.unite.Util.DataEnums;


public class CustomApplication extends MultiDexApplication implements Application.ActivityLifecycleCallbacks {

    private static Context context;
    private DataEnums dataEnums;

    @Override
    public void onCreate() {
        super.onCreate();

        context = this.getApplicationContext();
        dataEnums = new DataEnums();
        dataEnums.registerTypeConverter();
        KakaoSdk.init(this, getString(R.string.kakao_key));
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Glide.get(this).clearMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Glide.get(this).trimMemory(level);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Glide.get(this).trimMemory(TRIM_MEMORY_MODERATE);
    }
}
