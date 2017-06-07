package com.framgia.fdms.data.source;

import com.framgia.fdms.data.model.Category;
import com.framgia.fdms.data.model.Dashboard;
import com.framgia.fdms.data.model.Device;
import com.framgia.fdms.data.model.DeviceHistoryDetail;
import com.framgia.fdms.data.model.DeviceUsingHistory;
import com.framgia.fdms.data.model.Status;
import com.framgia.fdms.data.source.remote.DeviceRemoteDataSource;
import java.util.List;
import rx.Observable;

public class DeviceRepository {
    private DeviceRemoteDataSource mDeviceRemoteDataSource;

    public DeviceRepository(DeviceRemoteDataSource remoteDataSource) {
        mDeviceRemoteDataSource = remoteDataSource;
    }

    public Observable<List<Device>> getListDevices(String deviceName, int categoryId, int statusId,
            int page, int perPage) {
        return mDeviceRemoteDataSource.getListDevices(deviceName, categoryId, statusId, page,
                perPage);
    }

    public Observable<List<Category>> getListCategory() {
        return mDeviceRemoteDataSource.getListCategory();
    }

    public Observable<List<Status>> getListStatus() {
        return mDeviceRemoteDataSource.getListStatus();
    }

    public Observable<Device> registerdevice(Device device) {
        return mDeviceRemoteDataSource.registerdevice(device);
    }

    public Observable<Device> updateDevice(Device device) {
        return mDeviceRemoteDataSource.updateDevice(device);
    }

    public Observable<Device> getDeviceByQrCode(String qrCode) {
        return mDeviceRemoteDataSource.getDeviceByQrCode(qrCode);
    }

    public Observable<List<Dashboard>> getDashboardDevice() {
        return mDeviceRemoteDataSource.getDashboardDevice();
    }

    public Observable<List<DeviceUsingHistory>> getDeviceUsingHistory(int deviceId) {
        return mDeviceRemoteDataSource.getDeviceUsingHistory(deviceId);
    }

    public Observable<List<DeviceHistoryDetail>> getDeviceDetailHistory(int deviceId) {
        return mDeviceRemoteDataSource.getDeviceDetailHistory(deviceId);
    }

    public Observable<Device> getDevice(int deviceId) {
        return mDeviceRemoteDataSource.getDevice(deviceId);
    }

    public Observable<List<Device>> getTopDevice(int topDevice) {
        return mDeviceRemoteDataSource.getTopDevice(topDevice);
    }
}
