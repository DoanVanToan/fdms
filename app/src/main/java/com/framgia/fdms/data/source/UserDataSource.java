package com.framgia.fdms.data.source;

import com.framgia.fdms.data.model.Respone;
import com.framgia.fdms.data.model.User;
import com.framgia.fdms.data.source.api.request.RegisterRequest;
import rx.Observable;

/**
 * Created by levutantuan on 4/4/17.
 */

public class UserDataSource {

    public interface LocalDataSource {
        Observable<User> getCurrentUser();
    }

    public interface RemoteDataSource {
        Observable<Respone<User>> login(String userName, String passWord);

        Observable<User> register(RegisterRequest request);
    }
}
