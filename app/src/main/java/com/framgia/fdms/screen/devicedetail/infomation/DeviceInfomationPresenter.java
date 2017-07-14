package com.framgia.fdms.screen.devicedetail.infomation;

import com.framgia.fdms.data.model.Device;
import com.framgia.fdms.data.source.DeviceRepository;
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
    private Device mDevice;

    public DeviceInfomationPresenter(DeviceInfomationContract.ViewModel viewModel,
            DeviceRepository repository, Device device) {
        mViewModel = viewModel;
        mSubscription = new CompositeSubscription();
        mRepository = repository;
        mDevice = device;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
        mSubscription.clear();
    }
}
