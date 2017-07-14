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

        void onError();

        void onEditDevice();
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter {
    }
}
