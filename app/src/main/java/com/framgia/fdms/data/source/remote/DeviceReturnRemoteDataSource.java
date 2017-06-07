package com.framgia.fdms.data.source.remote;

import com.framgia.fdms.data.model.Device;
import com.framgia.fdms.data.model.Status;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;

/**
 * Created by Hoang Van Nha on 5/23/2017.
 * <></>
 */

public class DeviceReturnRemoteDataSource
        implements com.framgia.fdms.data.source.DeviceReturnDataSource.RemoteDataSource {

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
        List<Device> devices = new ArrayList<>();
        devices.add(new Device("HCM_06_506_006", "IPad", "IPad 2012"));
        devices.add(new Device("HCM_06_506_007", "IPad", "IPad 2013"));
        devices.add(new Device("HCM_06_506_008", "IPad", "IPad 2015"));
        devices.add(new Device("HCM_06_506_009", "IPad", "IPad 2016"));
        devices.add(new Device("HCM_06_506_0010", "IPad", "IPad 2017"));
        return Observable.just(devices);
    }
}
