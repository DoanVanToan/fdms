package com.framgia.fdms.screen.devicecreation;

import android.content.Intent;
import com.framgia.fdms.BasePresenter;
import com.framgia.fdms.BaseViewModel;
import com.framgia.fdms.data.model.Category;
import com.framgia.fdms.data.model.Device;
import com.framgia.fdms.data.model.Status;
import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 */
interface CreateDeviceContract {
    /**
     * View.
     */
    interface ViewModel extends BaseViewModel<Presenter> {
        void onDeviceCategoryLoaded(List<Category> categories);

        void onDeviceStatusLoaded(List<Status> statuses);

        void showProgressbar();

        void hideProgressbar();

        void onRegisterError();

        void onRegisterSuccess();

        void onInputProductionNameError();

        void onInputSerialNumberError();

        void onInputModellNumberError();

        void onInputDeviceCodeError();

        void onInputCategoryError();

        void onInputStatusError();

        void onAddImageClick();

        void onCreateDeviceClick();

        void onActivityResult(int requestCode, int resultCode, Intent data);

        void onUpdateError();

        void onUpdateSuccess(Device device);

        void onPickDateTimeClick();

        void onGetBranchSuccess(List<Status> branches);

        void onLoadError(String msg);

        void onInputBoughtDateError();

        void onInputOriginalPriceError();

        void onGetDeviceCodeSuccess(String deviceCode);

        void onPrintClick();

        void setProgressBar(int visibility);
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter {
        void registerDevice(Device device);

        void updateDevice(Device device);

        boolean validateDataInput(Device device);

        void getDeviceCode(int deviceCategoryId, int branchId);

        boolean validateDataEditDevice(Device device);
    }
}
