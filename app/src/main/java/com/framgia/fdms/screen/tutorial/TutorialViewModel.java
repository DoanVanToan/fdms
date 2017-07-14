package com.framgia.fdms.screen.tutorial;

import android.content.res.Resources;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import com.framgia.fdms.BR;
import com.framgia.fdms.R;
import com.framgia.fdms.data.model.Introduction;
import com.framgia.fdms.screen.ViewPagerScroll;
import com.framgia.fdms.screen.authenication.login.LoginActivity;
import com.framgia.fdms.screen.tutorial.introduction.IntroductionFragment;
import java.util.ArrayList;
import java.util.List;

/**
 * Exposes the data to be used in the Tutorial screen.
 */

public class TutorialViewModel extends BaseObservable
        implements TutorialContract.ViewModel, ViewPagerScroll {

    private TutorialContract.Presenter mPresenter;
    private TutorialPagerAdapter mAdapter;
    private AppCompatActivity mActivity;
    private int mSize;
    private int mTab = 0;

    public TutorialViewModel(AppCompatActivity activity) {
        mActivity = activity;

        Resources res = mActivity.getResources();
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(IntroductionFragment.newInstance(
                new Introduction.Builder().setTitle(res.getString(R.string.title_intro_title))
                        .setHeader(res.getString(R.string.title_intro_header))
                        .setContent(res.getString(R.string.title_intro_contet))
                        .setImage(ContextCompat.getDrawable(mActivity, R.drawable.ic_device_200dp))
                        .setColor(ContextCompat.getColor(mActivity, R.color.color_blue_170))
                        .create()));
        fragments.add(IntroductionFragment.newInstance(
                new Introduction.Builder().setTitle(res.getString(R.string.title_intro_title))
                        .setHeader(res.getString(R.string.title_intro_header))
                        .setContent(res.getString(R.string.title_intro_contet))
                        .setImage(ContextCompat.getDrawable(mActivity, R.drawable.ic_device_200dp))
                        .setColor(ContextCompat.getColor(mActivity, R.color.color_blue_170))
                        .create()));
        mAdapter = new TutorialPagerAdapter(activity.getSupportFragmentManager(), fragments);
        setAdapter(mAdapter);
        setSize(fragments.size() - 1);
    }

    public void onNextClick(ViewPager view, int tab) {
        if (tab < mSize) {
            int tabCur = tab + 1;
            view.setCurrentItem(tabCur);
            setTab(tabCur);
        }
    }

    public void onPreviousClick(ViewPager view, int tab) {
        if (tab > 0) {
            int tabCur = tab - 1;
            view.setCurrentItem(tabCur);
            setTab(tabCur);
        }
    }

    @Override
    public void onSkipClick() {
        mActivity.startActivity(LoginActivity.getInstance(mActivity));
        mActivity.finish();
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
    public void setPresenter(TutorialContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onCurrentPosition(int position) {
        setTab(position);
    }

    @Bindable
    public TutorialPagerAdapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(TutorialPagerAdapter adapter) {
        mAdapter = adapter;
        notifyPropertyChanged(BR.adapter);
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
    public int getSize() {
        return mSize;
    }

    public void setSize(int size) {
        mSize = size;
        notifyPropertyChanged(BR.size);
    }
}
