package com.framgia.fdms.data.source;

import com.framgia.fdms.data.model.Category;
import com.framgia.fdms.data.model.Dashboard;
import com.framgia.fdms.data.model.Device;
import com.framgia.fdms.data.model.DeviceHistoryDetail;
import com.framgia.fdms.data.model.DeviceUsingHistory;
import com.framgia.fdms.data.model.Status;
import java.util.List;
import rx.Observable;

/**
 * Created by Age on 4/3/2017.
 */
public class DeviceDataSource {
    public interface LocalDataSource {
    }

    public interface RemoteDataSource {
        Observable<List<Device>> getListDevices(String deviceName, int categoryId, int statusId,
                int page, int perPage);

        Observable<List<Category>> getListCategory();

        Observable<List<Status>> getListStatus();

        Observable<Device> registerdevice(Device registerdevice);

        Observable<Device> updateDevice(Device device);

        Observable<Device> getDeviceByQrCode(String qrCode);

        Observable<List<Dashboard>> getDashboardDevice();

        Observable<List<DeviceUsingHistory>> getDeviceUsingHistory(int deviceId);

        Observable<List<DeviceHistoryDetail>> getDeviceDetailHistory(int deviceId);

        Observable<Device> getDevice(int deviceId);

        Observable<List<Device>> getTopDevice(int topDevice);
    }
}
