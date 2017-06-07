package com.framgia.fdms.data.source.local;

import com.framgia.fdms.data.model.User;
import com.framgia.fdms.data.source.UserDataSource;
import com.framgia.fdms.data.source.local.sharepref.SharePreferenceImp;
import com.google.gson.Gson;
import rx.Observable;

import static com.framgia.fdms.data.source.local.sharepref.SharePreferenceKey.USER_PREFS;

/**
 * Created by MyPC on 01/06/2017.
 */

public class UserLocalDataSource implements UserDataSource.LocalDataSource {
    private SharePreferenceImp mSharePreference;

    public UserLocalDataSource(SharePreferenceImp sharePreference) {
        mSharePreference = sharePreference;
    }

    @Override
    public Observable<User> getCurrentUser() {
        String json = mSharePreference.get(USER_PREFS, String.class);
        User user = new Gson().fromJson(json, User.class);
        if (user != null) {
            return Observable.just(user);
        } else {
            return Observable.error(new NullPointerException());
        }
    }
}
