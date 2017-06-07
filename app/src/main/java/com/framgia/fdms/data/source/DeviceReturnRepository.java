package com.framgia.fdms.data.source;

import com.framgia.fdms.data.model.Device;
import com.framgia.fdms.data.model.Status;
import com.framgia.fdms.data.source.remote.DeviceReturnRemoteDataSource;
import java.util.List;
import rx.Observable;

/**
 * Created by Hoang Van Nha on 5/23/2017.
 * <></>
 */

public class DeviceReturnRepository {
    private DeviceReturnRemoteDataSource mDataSource;

    public DeviceReturnRepository(DeviceReturnRemoteDataSource dataSource) {
        mDataSource = dataSource;
    }

    public Observable<List<Status>> getBorrowers() {
        return mDataSource.getBorrowers();
    }

    public Observable<List<Device>> devicesOfBorrower() {
        return mDataSource.getDevicesOfBorrower();
    }
}
