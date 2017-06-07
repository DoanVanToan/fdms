package com.framgia.fdms.screen.main;

import android.content.Intent;
import com.framgia.fdms.BasePresenter;
import com.framgia.fdms.BaseViewModel;
import com.framgia.fdms.data.model.Device;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface MainContract {
    /**
     * View.
     */
    interface ViewModel extends BaseViewModel<Presenter> {
        void getResult(String resultQrCode);

        void onGetDecodeSuccess(Device device);

        void onGetDeviceError(String error);

        void onActivityResult(int requestCode, int resultCode, Intent data);

        void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults);
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter {
        void getDevice(String resultQrCode);
    }
}
