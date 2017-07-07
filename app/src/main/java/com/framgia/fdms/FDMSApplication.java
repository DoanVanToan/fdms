package com.framgia.fdms;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import com.framgia.fdms.data.model.Device;
import com.framgia.fdms.data.source.api.service.FDMSServiceClient;

/**
 * Created by Age on 4/3/2017.
 */
public class FDMSApplication extends Application {
    private static FDMSApplication sInstant;
    public static Device sUpdatedDevice;
    // Check ci

    @Override
    public void onCreate() {
        super.onCreate();
        FDMSServiceClient.initialize(this);
        sInstant = this;

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static FDMSApplication getInstant() {
        return sInstant;
    }
}
