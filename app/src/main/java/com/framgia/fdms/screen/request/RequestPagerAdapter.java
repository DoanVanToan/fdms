package com.framgia.fdms.screen.request;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.framgia.fdms.R;
import java.util.ArrayList;
import java.util.List;

import static com.framgia.fdms.screen.request.RequestPagerAdapter.RequestPage.MANAGER_REQUEST;
import static com.framgia.fdms.screen.request.RequestPagerAdapter.RequestPage.USER_REQUEST;

/**
 * Created by framgia on 19/05/2017.
 */

public class RequestPagerAdapter extends FragmentPagerAdapter {

    private static final int PAGE_COUNT = 2;
    private Context mContext;
    private List<Fragment> mFragments;
    private List<String> mTitles = new ArrayList<>();

    public RequestPagerAdapter(Context context, FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mContext = context;
        mFragments = fragments;
        mTitles.add(context.getString(R.string.title_my_request));
        mTitles.add(context.getString(R.string.title_manager_request));
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @IntDef({ MANAGER_REQUEST, USER_REQUEST })
    public @interface RequestPage {
        int USER_REQUEST = 0;
        int MANAGER_REQUEST = 1;
    }
}
