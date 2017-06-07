package com.framgia.fdms.screen.devicedetail;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.framgia.fdms.R;
import java.util.List;

import static com.framgia.fdms.screen.devicedetail.DeviceDetailPagerAdapter.DeviceDetailPage
        .DEVICE_HISTORY;
import static com.framgia.fdms.screen.devicedetail.DeviceDetailPagerAdapter.DeviceDetailPage
        .DEVICE_INFOMATION;
import static com.framgia.fdms.screen.devicedetail.DeviceDetailPagerAdapter.DeviceDetailPage
        .DEVICE_USING_HISTORY;

/**
 * Created by MyPC on 23/05/2017.
 */

public class DeviceDetailPagerAdapter extends FragmentPagerAdapter {
    public static final int PAGE_COUNT = 3;
    private Context mContext;
    private int mDeviceId;
    private List<Fragment> mFragments;
    private int mPosition;

    public DeviceDetailPagerAdapter(Context context, FragmentManager fm, List<Fragment> fragments,
            int deviceId) {
        super(fm);
        mContext = context;
        mDeviceId = deviceId;
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        mPosition = position;
        switch (position) {
            case DEVICE_INFOMATION:
                return mContext.getString(R.string.title_device_infomation);
            case DEVICE_HISTORY:
                return mContext.getString(R.string.title_device_history);
            case DEVICE_USING_HISTORY:
                return mContext.getString(R.string.title_using_history);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mFragments == null ? 0 : mFragments.size();
    }

    public int getPositionCurrent() {
        return mPosition;
    }

    @IntDef({ DEVICE_INFOMATION, DEVICE_HISTORY, DEVICE_USING_HISTORY })
    public @interface DeviceDetailPage {
        int DEVICE_INFOMATION = 0;
        int DEVICE_HISTORY = 1;
        int DEVICE_USING_HISTORY = 2;
    }
}
