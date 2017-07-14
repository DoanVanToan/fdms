package com.framgia.fdms.data.source.api.service;

import android.app.Application;
import android.support.annotation.NonNull;
import com.framgia.fdms.utils.Constant;

public class FDMSServiceClient extends ServiceClient {
    private static FDMSApi sMFDMSApiInstance;

    public static void initialize(@NonNull Application application) {
        sMFDMSApiInstance = createService(application, Constant.END_POINT_URL, FDMSApi.class);
    }

    public static FDMSApi getInstance() {
        if (sMFDMSApiInstance == null) {
            throw new RuntimeException("Need call method FDMSServiceClient#initialize() first");
        }
        return sMFDMSApiInstance;
    }
}
