package com.framgia.fdms.screen.devicedetail.usinghistory;

import com.framgia.fdms.data.model.DeviceUsingHistory;
import com.framgia.fdms.data.model.Status;
import com.framgia.fdms.data.source.DeviceRepository;
import java.util.List;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Listens to user actions from the UI ({@link DeviceUsingHistoryFragment}), retrieves the data and
 * updates
 * the UI as required.
 */
final class DeviceUsingHistoryPresenter implements DeviceUsingHistoryContract.Presenter {

    private final DeviceUsingHistoryContract.ViewModel mViewModel;
    private DeviceRepository mRepository;
    private CompositeSubscription mCompositeSubscription;
    private int mDeviceId = -1;

    public DeviceUsingHistoryPresenter(DeviceUsingHistoryContract.ViewModel viewModel,
            DeviceRepository repository, int deviceId) {
        mViewModel = viewModel;
        mCompositeSubscription = new CompositeSubscription();
        mRepository = repository;
        mDeviceId = deviceId;
        getUsingHistoryDevice(mDeviceId);
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
        mCompositeSubscription.clear();
    }

    @Override
    public void getUsingHistoryDevice(int deviceID) {
        mViewModel.showProgressbar();
        Subscription subscription = mRepository.getDeviceUsingHistory(deviceID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<DeviceUsingHistory>>() {
                    @Override
                    public void onCompleted() {
                        mViewModel.hideProgressbar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mViewModel.onGetUsingHistoryDeviceFailed(e.getMessage());
                        mViewModel.hideProgressbar();
                    }

                    @Override
                    public void onNext(List<DeviceUsingHistory> histories) {
                        mViewModel.onGetUsingHistoryDeviceSuccess(histories);
                    }
                });
        mCompositeSubscription.add(subscription);
    }

    @Override
    public void onLoadMore() {
        // TODO: 23/05/2017 later
        mViewModel.hideProgressbar();
    }

    @Override
    public void getData(Status relative, Status status) {
        // no ops
    }
}
