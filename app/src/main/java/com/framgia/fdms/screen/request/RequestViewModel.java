package com.framgia.fdms.screen.request;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.IntDef;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.framgia.fdms.BR;
import com.framgia.fdms.R;
import com.framgia.fdms.data.model.User;
import com.framgia.fdms.screen.ViewPagerScroll;
import com.framgia.fdms.screen.request.requestmanager.RequestManagerFragment;
import com.framgia.fdms.screen.request.userrequest.UserRequestFragment;
import com.framgia.fdms.screen.requestcreation.RequestCreationActivity;
import com.framgia.fdms.widget.FDMSShowcaseSequence;

import java.util.ArrayList;
import java.util.List;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

import static android.app.Activity.RESULT_OK;
import static com.framgia.fdms.screen.request.RequestViewModel.Tab.TAB_MANAGER_REQUEST;
import static com.framgia.fdms.screen.request.RequestViewModel.Tab.TAB_MY_REQUEST;
import static com.framgia.fdms.utils.Constant.RequestConstant.REQUEST_CREATE_REQUEST;

/**
 * Exposes the data to be used in the Request screen.
 */
public class RequestViewModel extends BaseObservable
    implements RequestContract.ViewModel, ViewPagerScroll {
    private RequestContract.Presenter mPresenter;
    private RequestPagerAdapter mAdapter;
    private RequestFragment mFragment;
    private Context mContext;
    private boolean mIsBo;
    private int mTab = TAB_MY_REQUEST;
    private FDMSShowcaseSequence mSequence;

    public RequestViewModel(RequestFragment fragment) {
        mFragment = fragment;
        mContext = fragment.getContext();
        mSequence = new FDMSShowcaseSequence(fragment.getActivity());
        ShowcaseConfig config = new ShowcaseConfig();
        config.setMaskColor(R.color.color_black_transprarent);
        mSequence.setConfig(config);
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
    public void setPresenter(RequestContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onCurrentPosition(int position) {
        setTab(position);
    }

    @Override
    public void onClickChangeTab(ViewPager viewPager, int currentTab) {
        viewPager.setCurrentItem(currentTab);
    }

    @Override
    public void onShowCase() {
        mSequence.start();
        mSequence.setOnItemDismissedListener(
            new MaterialShowcaseSequence.OnSequenceItemDismissedListener() {
                @Override
                public void onDismiss(MaterialShowcaseView materialShowcaseView, int i) {
                    mSequence.setCount(mSequence.getCount() - 1);
                    if (mSequence.getCount() == 0) {
                        mPresenter.saveShowCase();
                    }
                }
            });
    }

    @Override
    public void onRegisterRequestClick() {
        mFragment.startActivityForResult(
            RequestCreationActivity.getInstance(mFragment.getActivity()),
            REQUEST_CREATE_REQUEST);
    }

    @Override
    public void onGetCurrentUserSuccess(User user) {
        String role = user.getRole();
        if (role == null) return;
        setBo(user.isBo());
        List<BaseRequestFragment> fragments = new ArrayList<>();
        fragments.add(UserRequestFragment.newInstance());
        if (mIsBo) fragments.add(RequestManagerFragment.newInstance());
        mAdapter =
            new RequestPagerAdapter(mContext, mFragment.getChildFragmentManager(), fragments);
        setAdapter(mAdapter);
    }

    @Override
    public void onError(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;
        if (requestCode == REQUEST_CREATE_REQUEST) {
            mAdapter.refreshData();
        }
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
    public RequestPagerAdapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(RequestPagerAdapter adapter) {
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

    @IntDef({TAB_MY_REQUEST, TAB_MANAGER_REQUEST})
    public @interface Tab {
        int TAB_MY_REQUEST = 0;
        int TAB_MANAGER_REQUEST = 1;
    }

    @Bindable
    public FDMSShowcaseSequence getSequence() {
        return mSequence;
    }

    public void setSequence(FDMSShowcaseSequence sequence) {
        mSequence = sequence;
        notifyPropertyChanged(BR.sequence);
    }
}
