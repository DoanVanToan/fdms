package com.framgia.fdms.data.source;

import com.framgia.fdms.data.model.Respone;
import com.framgia.fdms.data.model.User;
import com.framgia.fdms.data.source.api.request.RegisterRequest;
import com.framgia.fdms.data.source.local.UserLocalDataSource;
import com.framgia.fdms.data.source.remote.UserRemoteDataSource;
import rx.Observable;

/**
 * Created by levutantuan on 4/4/17.
 */

public class UserRepository {

    private UserRemoteDataSource mUserRemoteDataSource;
    private UserLocalDataSource mUserLocalDataSource;

    public UserRepository(UserRemoteDataSource remoteDataSource) {
        mUserRemoteDataSource = remoteDataSource;
    }

    public UserRepository(UserLocalDataSource userLocalDataSource) {
        mUserLocalDataSource = userLocalDataSource;
    }

    public Observable<Respone<User>> login(String userName, String passWord) {
        return mUserRemoteDataSource.login(userName, passWord);
    }

    public Observable<User> register(RegisterRequest request) {
        return mUserRemoteDataSource.register(request);
    }

    public Observable<User> getCurrentUser() {
        return mUserLocalDataSource.getCurrentUser();
    }
}
