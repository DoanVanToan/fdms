package com.framgia.fdms.screen.device;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.IntDef;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.android.databinding.library.baseAdapters.BR;
import com.framgia.fdms.data.model.User;
import com.framgia.fdms.screen.ViewPagerScroll;
import com.framgia.fdms.screen.device.listdevice.ListDeviceFragment;
import com.framgia.fdms.screen.producer.marker.MarkerFragment;
import com.framgia.fdms.screen.producer.vendor.VendorFragment;

import java.util.ArrayList;
import java.util.List;

import static com.framgia.fdms.screen.device.DeviceViewModel.Tab.TAB_MAKER;
import static com.framgia.fdms.screen.device.DeviceViewModel.Tab.TAB_MANAGE_DEVICE;
import static com.framgia.fdms.screen.device.DeviceViewModel.Tab.TAB_MY_DEVICE;
import static com.framgia.fdms.screen.device.DeviceViewModel.Tab.TAB_VENDOR;

/**
 * Exposes the data to be used in the Device screen.
 */
public class DeviceViewModel extends BaseObservable
    implements DeviceContract.ViewModel, ViewPagerScroll {
    private final Fragment mFragment;
    private DeviceContract.Presenter mPresenter;
    private ViewPagerAdapter mAdapter;
    private int mTab = TAB_MY_DEVICE;
    private boolean mIsBo;

    public DeviceViewModel(Fragment fragment) {
        mFragment = fragment;
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(ListDeviceFragment.newInstance(TAB_MY_DEVICE));
        fragments.add(ListDeviceFragment.newInstance(TAB_MANAGE_DEVICE));
        mAdapter = new ViewPagerAdapter(mFragment.getChildFragmentManager(), fragments);
        setAdapter(mAdapter);
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
    public void setPresenter(DeviceContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setupViewPager(User user) {
        String role = user.getRole();
        if (role == null) return;
        setBo(user.isBo());
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(ListDeviceFragment.newInstance(TAB_MY_DEVICE));
        if (mIsBo) {
            fragments.add(ListDeviceFragment.newInstance(TAB_MANAGE_DEVICE));
            fragments.add(VendorFragment.newInstance());
            fragments.add(MarkerFragment.newInstance(TAB_MAKER));
        }
        mAdapter = new ViewPagerAdapter(mFragment.getChildFragmentManager(), fragments);
        setAdapter(mAdapter);
    }

    @Override
    public void onError(String message) {
        Snackbar.make(mFragment.getActivity().findViewById(android.R.id.content), message,
            Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onCurrentPosition(int position) {
        setTab(position);
    }

    @Override
    public void onClickChangeTab(ViewPager viewPager, @Tab int currentTab) {
        viewPager.setCurrentItem(currentTab);
        mTab = currentTab;
    }

    @Bindable
    public int getTab() {
        return mTab;
    }

    public void setTab(int tab) {
        mTab = tab;
        notifyPropertyChanged(BR.tab);
    }

    @Bindable
    public boolean isBo() {
        return mIsBo;
    }

    public void setBo(boolean bo) {
        mIsBo = bo;
        notifyPropertyChanged(BR.bo);
    }

    @Bindable
    public ViewPagerAdapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(ViewPagerAdapter adapter) {
        mAdapter = adapter;
        notifyPropertyChanged(BR.adapter);
    }

    @IntDef({TAB_MY_DEVICE, TAB_MANAGE_DEVICE, TAB_VENDOR, TAB_MAKER})
    public @interface Tab {
        int TAB_MY_DEVICE = 0;
        int TAB_MANAGE_DEVICE = 1;
        int TAB_VENDOR = 2;
        int TAB_MAKER = 3;
    }
}
