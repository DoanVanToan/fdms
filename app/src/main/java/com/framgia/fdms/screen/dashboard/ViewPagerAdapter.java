package com.framgia.fdms.screen.dashboard;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.framgia.fdms.screen.dashboard.dashboarddetail.DashBoardDetailFragment;
import java.util.List;

import static com.framgia.fdms.screen.dashboard.DashboardViewModel.Tab.TAB_DEVIVE_DASH_BOARD;
import static com.framgia.fdms.screen.dashboard.DashboardViewModel.Tab.TAB_REQUEST_DASH_BOARD;
import static com.framgia.fdms.screen.dashboard.dashboarddetail.DashBoardDetailFragment
        .DEVICE_DASHBOARD;
import static com.framgia.fdms.screen.dashboard.dashboarddetail.DashBoardDetailFragment
        .REQUEST_DASHBOARD;

/**
 * Created by MyPC on 15/05/2017.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private static final int TAB_NUMBER = 2;
    private List<Fragment> mFragments;

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
