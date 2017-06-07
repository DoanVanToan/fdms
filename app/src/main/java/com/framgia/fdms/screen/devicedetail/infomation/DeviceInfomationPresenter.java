package com.framgia.fdms.screen.devicedetail.infomation;

import com.framgia.fdms.data.model.Device;
import com.framgia.fdms.data.source.DeviceRepository;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Listens to user actions from the UI ({@link DeviceInfomationFragment}), retrieves the data and
 * updates
 * the UI as required.
 */
final class DeviceInfomationPresenter implements DeviceInfomationContract.Presenter {

    private final DeviceInfomationContract.ViewModel mViewModel;
    private CompositeSubscription mSubscription;
    private DeviceRepository mRepository;
    private int mDevicecId;

    public DeviceInfomationPresenter(DeviceInfomationContract.ViewModel viewModel,
            DeviceRepository repository, int deviceId) {
        mViewModel = viewModel;
        mSubscription = new CompositeSubscription();
        mRepository = repository;
        mDevicecId = deviceId;
    }

    @Override
    public void onStart() {
        getDevice(mDevicecId);
    }

    @Override
    public void onStop() {
        mSubscription.clear();
    }

    @Override
    public void getDevice(int deviceId) {
        Subscription subscription = mRepository.getDevice(deviceId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mViewModel.showProgressbar();
                    }
                })
                .subscribe(new Action1<Device>() {
                    @Override
                    public void call(Device device) {
                        mViewModel.onGetDeviceSuccess(device);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mViewModel.hideProgressbar();
                        mViewModel.onError(throwable.getMessage());
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        mViewModel.hideProgressbar();
                    }
                });
        mSubscription.add(subscription);
    }
}
