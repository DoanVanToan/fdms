package com.framgia.fdms.screen.returndevice;

import com.framgia.fdms.data.model.Device;
import com.framgia.fdms.data.model.Status;
import com.framgia.fdms.data.source.DeviceRepository;
import com.framgia.fdms.data.source.DeviceReturnRepository;
import com.framgia.fdms.data.source.StatusRepository;
import java.util.List;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Listens to user actions from the UI ({@link ReturnDeviceActivity}), retrieves the data and
 * updates
 * the UI as required.
 */
public final class ReturnDevicePresenter implements ReturnDeviceContract.Presenter {
    private static final String TAG = ReturnDevicePresenter.class.getName();

    private final ReturnDeviceContract.ViewModel mViewModel;
    private CompositeSubscription mSubscription;
    private StatusRepository mRepository;
    private DeviceRepository mDeviceRepository;
    private DeviceReturnRepository mDeviceReturnRepository;

    public ReturnDevicePresenter(ReturnDeviceContract.ViewModel viewModel,
            StatusRepository repository, DeviceReturnRepository deviceReturnRepository,
            DeviceRepository deviceRepository) {
        mViewModel = viewModel;
        mRepository = repository;
        mDeviceReturnRepository = deviceReturnRepository;
        mDeviceRepository = deviceRepository;
        mSubscription = new CompositeSubscription();
        getListAssign();
    }

    @Override
    public void getListAssign() {
        Subscription subscription = mDeviceReturnRepository.getBorrowers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mViewModel.showProgressbar();
                    }
                })
                .subscribe(new Subscriber<List<Status>>() {
                    @Override
                    public void onCompleted() {
                        mViewModel.hideProgressbar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mViewModel.hideProgressbar();
                        mViewModel.onLoadError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<Status> statuses) {
                        mViewModel.onGetAssignedSuccess(statuses);
                    }
                });
        mSubscription.add(subscription);
    }

    @Override
    public void getDevicesOfBorrower() {
        Subscription subscription = mDeviceReturnRepository.devicesOfBorrower()
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
                    }

                    @Override
                    public void onNext(List<Device> devices) {
                        mViewModel.onDeviceLoaded(devices);
                    }
                });
        mSubscription.add(subscription);
    }

    @Override
    public void getDeviceByCode(String codeDevice, final boolean isUserOther) {
        Subscription subscription = mDeviceRepository.getDeviceByQrCode(codeDevice)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mViewModel.showProgressbar();
                    }
                })
                .subscribe(new Subscriber<Device>() {
                    @Override
                    public void onCompleted() {
                        mViewModel.hideProgressbar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mViewModel.hideProgressbar();
                    }

                    @Override
                    public void onNext(Device device) {
                        if (isUserOther) {
                            mViewModel.onGetDeviceUserOtherSuccess(device);
                        } else {
                            mViewModel.onGetDeviceSuccess(device);
                        }
                    }
                });
        mSubscription.add(subscription);
    }

    @Override
    public void onStart() {
        getListAssign();
    }

    @Override
    public void onStop() {
        mSubscription.clear();
    }
}
