package com.framgia.fdms.screen.notification;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.android.databinding.library.baseAdapters.BR;
import com.framgia.fdms.R;
import com.framgia.fdms.data.model.Notification;
import java.util.ArrayList;
import java.util.List;

/**
 * Exposes the data to be used in the Notification screen.
 */

public class NotificationViewModel extends BaseObservable
        implements NotificationContract.ViewModel {

    private final AppCompatActivity mActivity;
    private NotificationContract.Presenter mPresenter;
    private NotificationAdapter mAdapter;
    private List<Notification> mNotifications = new ArrayList<>();

    public NotificationViewModel(AppCompatActivity activity) {
        mActivity = activity;
        mAdapter = new NotificationAdapter(this, mNotifications);
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
    public void setPresenter(NotificationContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onClickNotification(Notification notification, int position) {
        // TODO: 6/1/2017 switch home fragment 
    }

    @Override
    public void onLoadNotificationSuccess(List<Notification> notifications) {
        mNotifications.clear();
        mNotifications.addAll(notifications);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadNotificationFails() {
        Toast.makeText(mActivity.getApplicationContext(), R.string.msg_load_data_fails,
                Toast.LENGTH_SHORT).show();
    }

    @Bindable
    public NotificationAdapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(NotificationAdapter adapter) {
        mAdapter = adapter;
        notifyPropertyChanged(BR.adapter);
    }

    public AppCompatActivity getActivity() {
        return mActivity;
    }
}
