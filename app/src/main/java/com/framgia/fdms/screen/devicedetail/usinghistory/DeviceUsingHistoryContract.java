package com.framgia.fdms.screen.devicedetail.usinghistory;

import com.framgia.fdms.BaseFragmentContract;
import com.framgia.fdms.BasePresenter;
import com.framgia.fdms.BaseViewModel;
import com.framgia.fdms.data.model.DeviceUsingHistory;
import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 */
interface DeviceUsingHistoryContract {
    /**
     * View.
     */
    interface ViewModel extends BaseFragmentContract.ViewModel {
        void onGetUsingHistoryDeviceSuccess(List<DeviceUsingHistory> histories);

        void onGetUsingHistoryDeviceFailed(String msg);
    }

    /**
     * Presenter.
     */
    interface Presenter extends BaseFragmentContract.Presenter {
        void getUsingHistoryDevice(int deviceID);
    }
}
