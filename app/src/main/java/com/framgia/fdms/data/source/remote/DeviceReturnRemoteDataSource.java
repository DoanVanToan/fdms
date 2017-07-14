package com.framgia.fdms.data.source.remote;

import com.framgia.fdms.data.model.Device;
import com.framgia.fdms.data.model.Respone;
import com.framgia.fdms.data.model.Status;
import com.framgia.fdms.data.source.api.service.FDMSApi;
import com.framgia.fdms.data.source.api.service.FDMSServiceClient;
import com.framgia.fdms.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Hoang Van Nha on 5/23/2017.
 * <></>
 */

public class DeviceReturnRemoteDataSource
        implements com.framgia.fdms.data.source.DeviceReturnDataSource.RemoteDataSource {

    private static DeviceReturnRemoteDataSource sInstances;
    private FDMSApi mFDMSApi;

    private DeviceReturnRemoteDataSource() {
        mFDMSApi = FDMSServiceClient.getInstance();
    }

    public static DeviceReturnRemoteDataSource getInstances() {
        if (sInstances == null) sInstances = new DeviceReturnRemoteDataSource();
        return sInstances;
    }

    @Override
    public Observable<List<Status>> getBorrowers() {
        // TODO: later
        List<Status> borrowers = new ArrayList<>();
        borrowers.add(new Status(1, "Chu Anh Tuan"));
        borrowers.add(new Status(2, "Tran Xuan Thang"));
        borrowers.add(new Status(3, "Nguyen Binh Dieu"));
        borrowers.add(new Status(4, "Nguyen Van Tuan"));
        borrowers.add(new Status(5, "Doan Van Toan"));
        borrowers.add(new Status(6, "Nguyen Thi Thuy Dung"));
        return Observable.just(borrowers);
    }

    @Override
    public Observable<List<Device>> getDevicesOfBorrower() {
        return mFDMSApi.getDevicesBorrow()
                .flatMap(new Func1<Respone<List<Device>>, Observable<List<Device>>>() {

                    @Override
                    public Observable<List<Device>> call(Respone<List<Device>> listRespone) {
                        return Utils.getResponse(listRespone);
                    }
                });
    }
}
