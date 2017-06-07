package com.framgia.fdms.screen.devicedetail.usinghistory;

import android.content.Context;
import android.databinding.Bindable;
import android.widget.Toast;
import com.android.databinding.library.baseAdapters.BR;
import com.framgia.fdms.BaseFragmentContract;
import com.framgia.fdms.BaseFragmentModel;
import com.framgia.fdms.data.model.DeviceUsingHistory;
import java.util.List;

/**
 * Exposes the data to be used in the UsingHistory screen.
 */

public class DeviceUsingHistoryViewModel extends BaseFragmentModel
        implements DeviceUsingHistoryContract.ViewModel {

    private BaseFragmentContract.Presenter mPresenter;
    private DeviceUsingAdapter mAdapter;
    private Context mContext;

    public DeviceUsingHistoryViewModel(Context context) {
        mContext = context;
        mAdapter = new DeviceUsingAdapter();
    }

    @Override
    public void onStart() {
        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
    }

    public void setPresenter(BaseFragmentContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Bindable
    public DeviceUsingAdapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(DeviceUsingAdapter adapter) {
        mAdapter = adapter;
        notifyPropertyChanged(BR.adapter);
    }

    @Override
    public void onGetUsingHistoryDeviceSuccess(List<DeviceUsingHistory> histories) {
        mAdapter.addData(histories);
    }

    @Override
    public void onGetUsingHistoryDeviceFailed(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }
}
