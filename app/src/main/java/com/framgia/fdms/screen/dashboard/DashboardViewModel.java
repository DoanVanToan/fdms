package com.framgia.fdms.screen.dashboard;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.IntDef;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.Toast;
import com.framgia.fdms.BR;
import com.framgia.fdms.data.model.User;
import com.framgia.fdms.screen.dashboard.dashboarddetail.DashBoardDetailFragment;
import com.framgia.fdms.screen.notification.NotificationActivity;
import java.util.ArrayList;
import java.util.List;

import static com.framgia.fdms.screen.dashboard.DashboardViewModel.Tab.TAB_DEVIVE_DASH_BOARD;
import static com.framgia.fdms.screen.dashboard.DashboardViewModel.Tab.TAB_REQUEST_DASH_BOARD;
import static com.framgia.fdms.screen.dashboard.dashboarddetail.DashBoardDetailFragment
        .DEVICE_DASHBOARD;
import static com.framgia.fdms.screen.dashboard.dashboarddetail.DashBoardDetailFragment
        .REQUEST_DASHBOARD;

/**
 * Exposes the data to be used in the Dashboard screen.
 */

public class DashboardViewModel extends BaseObservable implements DashboardContract.ViewModel {

    private final int NUMBER_NOTIFICATION = 5;
    private final int MIN_NUMBER_NOTIFICATION = 0;
    private DashboardContract.Presenter mPresenter;
    private ViewPagerAdapter mPagerAdapter;
    private int mTab = TAB_REQUEST_DASH_BOARD;
    private boolean mIsBoRole;
    private Fragment mFragment;
    private Context mContext;
    private int mNumberNotification = NUMBER_NOTIFICATION;

    public DashboardViewModel(Fragment fragment) {
        mFragment = fragment;
        mContext = fragment.getContext();
    }

    public void onClickChangeTab(ViewPager viewpager, @Tab int tab) {
        viewpager.setCurrentItem(tab);
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
    public void setPresenter(DashboardContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Bindable
    public ViewPagerAdapter getPagerAdapter() {
        return mPagerAdapter;
    }

    public void setPagerAdapter(ViewPagerAdapter pagerAdapter) {
        mPagerAdapter = pagerAdapter;
        notifyPropertyChanged(BR.pagerAdapter);
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
    public boolean isBoRole() {
        return mIsBoRole;
    }

    public void setBoRole(boolean boRole) {
        mIsBoRole = boRole;
        notifyPropertyChanged(BR.boRole);
    }

    @Override
    public void setupViewPager(User user) {
        String role = user.getRole();
        if (role == null) return;

        setBoRole(user.isBo());

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(DashBoardDetailFragment.newInstance(REQUEST_DASHBOARD));
        if (mIsBoRole) fragments.add(DashBoardDetailFragment.newInstance(DEVICE_DASHBOARD));
        mPagerAdapter = new ViewPagerAdapter(mFragment.getChildFragmentManager(), fragments);
        setPagerAdapter(mPagerAdapter);
    }

    @Override
    public void onError(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStartNotificationView() {
        mFragment.startActivity(NotificationActivity.getInstances(mContext));
        mNumberNotification = MIN_NUMBER_NOTIFICATION;
    }

    @Bindable
    public int getNumberNotification() {
        return mNumberNotification;
    }

    public void setNumberNotification(int numberNotification) {
        mNumberNotification = numberNotification;
        notifyPropertyChanged(BR.numberNotification);
    }

    @IntDef({
            TAB_DEVIVE_DASH_BOARD, TAB_REQUEST_DASH_BOARD
    })
    public @interface Tab {
        int TAB_REQUEST_DASH_BOARD = 0;
        int TAB_DEVIVE_DASH_BOARD = 1;
    }
}
