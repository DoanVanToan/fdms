package com.framgia.fdms.screen.devicedetail;

import com.framgia.fdms.data.model.Device;
import com.framgia.fdms.data.source.DeviceRepository;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Listens to user actions from the UI ({@link DeviceDetailActivity}), retrieves the data and
 * updates
 * the UI as required.
 */
final class DeviceDetailPresenter implements DeviceDetailContract.Presenter {

    private final DeviceDetailContract.ViewModel mViewModel;
    private CompositeSubscription mSubscription;
    private DeviceRepository mRepository;
    private Device mDevice;

    public DeviceDetailPresenter(DeviceDetailContract.ViewModel viewModel,
            DeviceRepository repository, Device device) {
        mViewModel = viewModel;
        mSubscription = new CompositeSubscription();
        mRepository = repository;
        mDevice = device;
        getDevice(mDevice);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        mSubscription.clear();
    }

    @Override
    public void getDevice(final Device localDevice) {
        Subscription subscription = mRepository.getDevice(localDevice.getId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new Action1<Device>() {
                    @Override
                    public void call(Device device) {
                        localDevice.cloneDevice(device);
                        mViewModel.onGetDeviceSuccess(localDevice);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mViewModel.onGetDeviceError();
                    }
                });
        mSubscription.add(subscription);
    }
}
