package com.framgia.fdms.data.source.remote;

import com.framgia.fdms.data.model.Respone;
import com.framgia.fdms.data.model.User;
import com.framgia.fdms.data.source.UserDataSource;
import com.framgia.fdms.data.source.api.request.RegisterRequest;
import com.framgia.fdms.data.source.api.service.FDMSApi;
import com.framgia.fdms.data.source.api.service.FDMSServiceClient;
import com.framgia.fdms.utils.Utils;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by levutantuan on 4/4/17.
 */

public class UserRemoteDataSource implements UserDataSource.RemoteDataSource {
    private final String FIRST_NAME = "user[first_name]";
    private final String LAST_NAME = "user[last_name]";
    private final String ADDRESS = "user[address]";
    private final String BIRTHDAY = "user[birthday]";
    private final String CHATWORK_ID = "user[chatwork_id]";
    private final String AVATAR = "user[avatar]";

    private FDMSApi mFDMSApi;

    public UserRemoteDataSource() {
        mFDMSApi = FDMSServiceClient.getInstance();
    }

    @Override
    public Observable<Respone<User>> login(String userName, String passWord) {
        return mFDMSApi.login(userName, passWord);
    }

    @Override
    public Observable<User> register(RegisterRequest request) {
        // TODO: 4/4/17 replace by call API later
        return Observable.just(null);
    }

    @Override
    public Observable<User> updateProfile(User user) {
        Map<String, RequestBody> params = new HashMap<>();
        if (user.getFirstName() != null) {
            params.put(FIRST_NAME, createPartFromString(user.getFirstName()));
        }
        if (user.getLastName() != null) {
            params.put(LAST_NAME, createPartFromString(user.getLastName()));
        }
        if (user.getAddress() != null) params.put(ADDRESS, createPartFromString(user.getAddress()));
        params.put(BIRTHDAY, createPartFromString(String.valueOf(user.getBirthday())));
        if (user.getChatWorkId() != null) {
            params.put(CHATWORK_ID, createPartFromString(user.getChatWorkId()));
        }

        MultipartBody.Part avatar = null;
        if (user.getAvatar() != null && user.getAvatar().getUrl() != null) {
            File file = new File(user.getAvatar().getUrl());
            if (file.exists()) {
                RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
                avatar = MultipartBody.Part.createFormData(AVATAR, file.getName(), requestBody);
            }
        }
        return mFDMSApi.updateProfile(user.getId(), params, avatar)
                .flatMap(new Func1<Respone<User>, Observable<User>>() {
                    @Override
                    public Observable<User> call(Respone<User> userRespone) {
                        return Utils.getResponse(userRespone);
                    }
                });
    }

    private RequestBody createPartFromString(String partString) {
        return RequestBody.create(MultipartBody.FORM, partString);
    }
}
