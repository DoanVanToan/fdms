package com.framgia.fdms.screen.devicedetail;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.android.databinding.library.baseAdapters.BR;
import com.framgia.fdms.data.model.Device;
import com.framgia.fdms.screen.devicedetail.infomation.DeviceInfomationFragment;
import com.framgia.fdms.screen.devicedetail.usinghistory.DeviceUsingHistoryFragment;
import com.framgia.fdms.screen.historydetail.DeviceDetailHistoryFragment;
import java.util.ArrayList;
import java.util.List;

import static com.framgia.fdms.screen.devicedetail.DeviceDetailPagerAdapter.DeviceDetailPage
        .DEVICE_INFOMATION;

/**
 * Exposes the data to be used in the Devicedetail screen.
 */

public class DeviceDetailViewModel extends BaseObservable
        implements DeviceDetailContract.ViewModel {

    private DeviceDetailContract.Presenter mPresenter;
    private DeviceDetailPagerAdapter mAdapter;
    private Context mContext;
    private AppCompatActivity mActivity;
    private DeviceInfomationFragment mInfomationFragment;
    private ObservableInt mFloatingVisible = new ObservableInt(View.VISIBLE);
    private Device mDevice = new Device();
    private ObservableField<Integer> mProgressBarVisibility = new ObservableField<>();

    public DeviceDetailViewModel(AppCompatActivity activity, int deviceId) {
        mActivity = activity;
        mContext = mActivity.getApplicationContext();
        mInfomationFragment = DeviceInfomationFragment.newInstance(deviceId);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(mInfomationFragment);
        fragments.add(DeviceDetailHistoryFragment.newInstance(deviceId));
        fragments.add(DeviceUsingHistoryFragment.newInstance());

        mAdapter = new DeviceDetailPagerAdapter(mContext, mActivity.getSupportFragmentManager(),
                fragments, deviceId);
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
    public void setPresenter(DeviceDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onEditDevice() {
        if (mInfomationFragment != null) mInfomationFragment.onStartEditDevice();
    }

    @Override
    public void onGetDeviceSuccess(Device device) {
        setDevice(device);
        mActivity.setTitle(device.getProductionName());
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

    public void updateFloadtingVisible(int position) {
        if (position == DEVICE_INFOMATION) {
            mFloatingVisible.set(View.VISIBLE);
        } else {
            mFloatingVisible.set(View.GONE);
        }
    }

    public DeviceDetailPagerAdapter getAdapter() {
        return mAdapter;
    }

    public AppCompatActivity getActivity() {
        return mActivity;
    }

    public ObservableInt getFloatingVisible() {
        return mFloatingVisible;
    }
}
