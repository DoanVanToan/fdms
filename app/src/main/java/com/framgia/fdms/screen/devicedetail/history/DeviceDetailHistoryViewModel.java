package com.framgia.fdms.screen.devicedetail.history;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;
import com.framgia.fdms.BR;
import com.framgia.fdms.data.model.DeviceHistoryDetail;
import java.util.List;

/**
 * Exposes the data to be used in the DeviceHistoryDetail screen.
 */

public class DeviceDetailHistoryViewModel extends BaseObservable
        implements DeviceDetailHistoryContract.ViewModel {

    private DeviceDetailHistoryContract.Presenter mPresenter;
    private DeviceDetailHistoryAdapter mAdapter;
    private int mEmptyViewVisible = View.GONE;

    public DeviceDetailHistoryViewModel() {
        mAdapter = new DeviceDetailHistoryAdapter();
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
    public void setPresenter(DeviceDetailHistoryContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onGetDeviceHistoryFailed(String message) {
        // TODO: 23/05/2017 show snack bar later
    }

    @Override
    public void onGetDeviceHistorySuccess(List<DeviceHistoryDetail> deviceHistoryDetails) {
        if (deviceHistoryDetails != null && deviceHistoryDetails.size() != 0) {
            setEmptyViewVisible(View.GONE);
            mAdapter.addData(deviceHistoryDetails);
        } else {
            setEmptyViewVisible(View.VISIBLE);
        }
    }

    @Bindable
    public DeviceDetailHistoryAdapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(DeviceDetailHistoryAdapter adapter) {
        mAdapter = adapter;
        notifyPropertyChanged(BR.adapter);
    }

    @Bindable
    public int getEmptyViewVisible() {
        return mEmptyViewVisible;
    }

    public void setEmptyViewVisible(int emptyViewVisible) {
        mEmptyViewVisible = emptyViewVisible;
        notifyPropertyChanged(BR.emptyViewVisible);
    }
}
