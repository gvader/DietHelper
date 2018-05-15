package com.gvader.diethelper;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class MyApplication extends Application {
    private static MyApplication INSTANCE;
    private Context context;
    private SharedPreferences sharedPrefs;

    public static final String TAG = MyApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;

        INSTANCE.initializeInstance();
        context = getApplicationContext();
     }

    private void initializeInstance() {
        // set shared prefs
        sharedPrefs = this.getAppContext().getSharedPreferences("pref_key", MODE_PRIVATE);
    }

    @Override
    public void onTerminate() {
        // For terminate tasks;
        if( isFirstRun() ) {
            setRunned();
        }
        super.onTerminate();
    }

    public static MyApplication getInstance() {
        return INSTANCE;
    }

    public boolean isFirstRun() {
        // return true if the app is running for the first time
        return sharedPrefs.getBoolean("is_first_run", true);
    }

    private void setRunned() {
        // after a successful run, call this method to set first run false
        SharedPreferences.Editor edit = sharedPrefs.edit();
        edit.putBoolean("is_first_run", false);
        edit.apply();
    }

    public Context getAppContext() {
        return getApplicationContext();
    }

    public String getAppVersion() {
        String retval = "";
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.N) {
            retval= BuildConfig.VERSION_NAME;
        } else {
            PackageInfo pInfo;
            try {
                pInfo = context
                        .getPackageManager()
                        .getPackageInfo(getPackageName(), 0);
                retval = pInfo.versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return retval;
    }
}
