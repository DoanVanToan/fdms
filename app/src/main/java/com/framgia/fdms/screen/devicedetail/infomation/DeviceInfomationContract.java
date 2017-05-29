package com.framgia.fdms.screen.devicedetail.infomation;

import com.framgia.fdms.BasePresenter;
import com.framgia.fdms.BaseViewModel;
import com.framgia.fdms.data.model.Device;

/**
 * This specifies the contract between the view and the presenter.
 */
interface DeviceInfomationContract {
    /**
     * View.
     */
    interface ViewModel extends BaseViewModel<Presenter> {
        void onGetDeviceSuccess(Device device);

        void onError(String msg);

        void onEditDevice();

        void showProgressbar();

        void hideProgressbar();
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter {
        void getDevice(int deviceId);
    }
}