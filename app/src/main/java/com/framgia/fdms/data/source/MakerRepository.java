package com.framgia.fdms.data.source;

import com.framgia.fdms.data.model.Producer;

import java.util.List;

import rx.Observable;

/**
 * Created by beepi on 05/07/2017.
 */
public class MakerRepository implements MakerRepositoryContract {
    private MakerDataSource.RemoteDataSource mRemoteDataSource;
    private static MakerRepository sMakerRepository;

    public static MakerRepository getInstant(
        MakerDataSource.RemoteDataSource remoteDataSource) {
        if (sMakerRepository == null) {
            sMakerRepository = new MakerRepository(remoteDataSource);
        }
        return sMakerRepository;
    }

    public MakerRepository(MakerDataSource.RemoteDataSource remoteDataSource) {
        mRemoteDataSource = remoteDataSource;
    }

    @Override
    public Observable<List<Producer>> getMakers(int page, int perpage) {
        return mRemoteDataSource.getListMarkers(page, perpage);
    }
}
