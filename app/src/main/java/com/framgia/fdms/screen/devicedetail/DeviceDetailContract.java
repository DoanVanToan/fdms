package com.framgia.fdms.screen.devicedetail;

import com.framgia.fdms.BasePresenter;
import com.framgia.fdms.BaseViewModel;
import com.framgia.fdms.data.model.Device;

/**
 * This specifies the contract between the view and the presenter.
 */
interface DeviceDetailContract {
    /**
     * View.
     */
    interface ViewModel extends BaseViewModel<Presenter> {
        void onEditDevice();

        void onGetDeviceSuccess(Device device);
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter {
        void getDevice(int deviceId);
    }
}
