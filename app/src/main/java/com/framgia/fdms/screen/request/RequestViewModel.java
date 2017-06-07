package com.framgia.fdms.screen.request;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.v4.app.Fragment;
import android.widget.Toast;
import com.framgia.fdms.BR;
import com.framgia.fdms.data.model.User;
import com.framgia.fdms.screen.request.requestmanager.RequestManagerFragment;
import com.framgia.fdms.screen.request.userrequest.UserRequestFragment;
import com.framgia.fdms.screen.requestcreation.RequestCreationActivity;
import java.util.ArrayList;
import java.util.List;

/**
 * Exposes the data to be used in the Request screen.
 */

public class RequestViewModel extends BaseObservable implements RequestContract.ViewModel {

    private RequestContract.Presenter mPresenter;
    private RequestPagerAdapter mAdapter;
    private RequestFragment mFragment;
    private Context mContext;
    private boolean mIsBo;

    public RequestViewModel(RequestFragment fragment) {
        mFragment = fragment;
        mContext = fragment.getContext();
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

    @Bindable
    public RequestPagerAdapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(RequestPagerAdapter adapter) {
        mAdapter = adapter;
        notifyPropertyChanged(BR.adapter);
    }

    @Override
    public void onRegisterRequestClick() {
        mFragment.startActivity(RequestCreationActivity.getInstance(mFragment.getActivity()));
    }

    @Override
    public void onGetCurrentUserSuccess(User user) {
        String role = user.getRole();
        if (role == null) return;

        setBo(user.isBo());

        List<Fragment> fragments = new ArrayList<>();
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

    @Bindable
    public boolean isBo() {
        return mIsBo;
    }

    public void setBo(boolean bo) {
        mIsBo = bo;
        notifyPropertyChanged(BR.bo);
    }
}
