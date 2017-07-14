package com.framgia.fdms.screen.profile.chooseexport;

import com.framgia.fdms.data.model.Device;
import com.framgia.fdms.data.source.DeviceReturnRepository;
import java.util.List;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by tuanbg on 6/15/17.
 */

public class ChooseExportPresenter implements ChooseExportContract.Presenter {
    private ChooseExportContract.ViewModel mViewModel;
    private DeviceReturnRepository mDeviceRepository;
    private CompositeSubscription mCompositeSubscriptions = new CompositeSubscription();

    public ChooseExportPresenter(ChooseExportContract.ViewModel viewModel,
            DeviceReturnRepository deviceRepository) {
        mViewModel = viewModel;
        mDeviceRepository = deviceRepository;
        getListDevice();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        mCompositeSubscriptions.clear();
    }

    @Override
    public void getListDevice() {
        Subscription subscription = mDeviceRepository.devicesOfBorrower()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mViewModel.showProgressbar();
                    }
                })
                .subscribe(new Subscriber<List<Device>>() {
                    @Override
                    public void onCompleted() {
                        mViewModel.hideProgressbar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mViewModel.onError(e.getCause().getMessage());
                        mViewModel.hideProgressbar();
                    }

                    @Override
                    public void onNext(List<Device> devices) {
                        mViewModel.onDeviceLoaded(devices);
                    }
                });
        mCompositeSubscriptions.add(subscription);
    }
}
