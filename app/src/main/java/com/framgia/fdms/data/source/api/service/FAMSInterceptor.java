package com.framgia.fdms.data.source.api.service;

import com.framgia.fdms.FDMSApplication;
import com.framgia.fdms.data.model.User;
import com.framgia.fdms.data.source.local.sharepref.SharePreferenceImp;
import com.google.gson.Gson;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.framgia.fdms.data.source.local.sharepref.SharePreferenceKey.USER_PREFS;

/**
 * Created by beepi on 11/05/2017.
 */

public class FAMSInterceptor implements Interceptor {
    public static final String HEADER_ACCEPT = "Accept";
    public static final String HEADER_AUTHORIZE = "Authorization";
    public static final String HEADER_VALUE_ACCEPT = "application/json";

    public FAMSInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder().header(HEADER_ACCEPT, HEADER_VALUE_ACCEPT);
        String token = getToken();
        if (token != null) {
            builder.header(HEADER_AUTHORIZE, token);
        }
        return chain.proceed(builder.build());
    }

    public static String getToken() {
        SharePreferenceImp sharePreferenceImp =
                new SharePreferenceImp(FDMSApplication.getInstant());
        String json = sharePreferenceImp.get(USER_PREFS, String.class);
        User user = new Gson().fromJson(json, User.class);
        return user != null ? user.getToken() : null;
    }
}
