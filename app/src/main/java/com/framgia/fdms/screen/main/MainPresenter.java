package com.framgia.fdms.screen.main;

import com.framgia.fdms.data.model.Device;
import com.framgia.fdms.data.source.DeviceRepository;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Listens to user actions from the UI ({@link MainActivity}), retrieves the data and updates
 * the UI as required.
 */
final class MainPresenter implements MainContract.Presenter {
    private static final String TAG = MainPresenter.class.getName();
    private final MainContract.ViewModel mViewModel;
    private CompositeSubscription mSubscription;
    private DeviceRepository mDeviceRepository;

    public MainPresenter(MainContract.ViewModel viewModel, DeviceRepository deviceRepository) {
        mViewModel = viewModel;
        mSubscription = new CompositeSubscription();
        mDeviceRepository = deviceRepository;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
        mSubscription.clear();
    }

    @Override
    public void getDevice(String resultQrCode) {
        Subscription subscription = mDeviceRepository.getDeviceByQrCode(resultQrCode)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<Device>() {
                    @Override
                    public void call(Device device) {
                        mViewModel.onGetDecodeSuccess(device);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mViewModel.onGetDeviceError(throwable.getMessage());
                    }
                });
        mSubscription.add(subscription);
    }
}
