package com.framgia.fdms.data.source;

import com.framgia.fdms.data.model.Status;
import com.framgia.fdms.data.source.remote.StatusRemoteDataSource;
import java.util.List;
import rx.Observable;

/**
 * Created by MyPC on 05/05/2017.
 */

public class StatusRepository {
    private StatusRemoteDataSource mStatusRemoteDataSource;

    public StatusRepository(StatusRemoteDataSource statusRemoteDataSource) {
        mStatusRemoteDataSource = statusRemoteDataSource;
    }

    public Observable<List<Status>> getListStatus(){
        return mStatusRemoteDataSource.getListStatus();
    }
}