package com.framgia.fdms.screen.devicedetail.infomation;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.view.View;
import android.widget.Toast;
import com.android.databinding.library.baseAdapters.BR;
import com.framgia.fdms.data.model.Device;
import com.framgia.fdms.screen.devicecreation.CreateDeviceActivity;
import com.framgia.fdms.screen.devicecreation.DeviceStatusType;

/**
 * Exposes the data to be used in the Deviceinfomation screen.
 */

public class DeviceInfomationViewModel extends BaseObservable
        implements DeviceInfomationContract.ViewModel {

    private DeviceInfomationContract.Presenter mPresenter;
    private Device mDevice = new Device();
    private Context mContext;
    private ObservableField<Integer> mProgressBarVisibility = new ObservableField<>();

    public DeviceInfomationViewModel(Context context) {
        mContext = context;
    }

    @Override
    public void onStart() {
        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
    }

    @Override
    public void setPresenter(DeviceInfomationContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onGetDeviceSuccess(Device device) {
        setDevice(device);
    }

    @Override
    public void onEditDevice() {
        mContext.startActivity(
                CreateDeviceActivity.getInstance(mContext, mDevice, DeviceStatusType.EDIT));
    }

    @Override
    public void onError(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgressbar() {
        mProgressBarVisibility.set(View.VISIBLE);
    }

    @Override
    public void hideProgressbar() {
        mProgressBarVisibility.set(View.GONE);
    }

    public ObservableField<Integer> getProgressBarVisibility() {
        return mProgressBarVisibility;
    }

    @Bindable
    public Device getDevice() {
        return mDevice;
    }

    public void setDevice(Device device) {
        mDevice = device;
        notifyPropertyChanged(BR.device);
    }
}
