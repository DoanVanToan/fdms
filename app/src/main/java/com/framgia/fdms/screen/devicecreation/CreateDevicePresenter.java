package com.framgia.fdms.screen.devicecreation;

import android.text.TextUtils;
import com.framgia.fdms.data.model.Category;
import com.framgia.fdms.data.model.Device;
import com.framgia.fdms.data.model.Status;
import com.framgia.fdms.data.source.CategoryRepository;
import com.framgia.fdms.data.source.DeviceRepository;
import com.framgia.fdms.data.source.StatusRepository;
import java.util.List;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Listens to user actions from the UI ({@link CreateDeviceActivity}), retrieves the data and
 * updates
 * the UI as required.
 */
final class CreateDevicePresenter implements CreateDeviceContract.Presenter {
    private final CreateDeviceContract.ViewModel mViewModel;
    private CompositeSubscription mCompositeSubscription;
    private DeviceRepository mDeviceRepository;
    private StatusRepository mStatusRepository;
    private CategoryRepository mCategoryRepository;

    public CreateDevicePresenter(CreateDeviceContract.ViewModel viewModel,
            DeviceRepository deviceRepository, StatusRepository statusRepository,
            CategoryRepository categoryRepository) {
        mViewModel = viewModel;
        mDeviceRepository = deviceRepository;
        mCategoryRepository = categoryRepository;
        mStatusRepository = statusRepository;
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void onStart() {
        getListCategories();
        getListStatuses();
    }

    @Override
    public void onStop() {
        mCompositeSubscription.clear();
    }

    @Override
    public void registerDevice(Device device) {
        if (!validateDataInput(device)) {
            return;
        }
        Subscription subscription = mDeviceRepository.registerdevice(device)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Device>() {
                    @Override
                    public void call(Device device) {
                        mViewModel.onRegisterSuccess();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mViewModel.onRegisterError();
                    }
                });
        mCompositeSubscription.add(subscription);
    }

    @Override
    public void updateDevice(Device device) {
        Subscription subscription = mDeviceRepository.updateDevice(device)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Device>() {
                    @Override
                    public void call(Device device) {
                        mViewModel.onUpdateSuccess(device);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mViewModel.onUpdateError();
                    }
                });
        mCompositeSubscription.add(subscription);
    }

    public void getListCategories() {
        Subscription subscription = mCategoryRepository.getListCategory()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mViewModel.showProgressbar();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Category>>() {
                    @Override
                    public void call(List<Category> categories) {
                        mViewModel.onDeviceCategoryLoaded(categories);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mViewModel.onInputCategoryError();
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        mViewModel.hideProgressbar();
                    }
                });
        mCompositeSubscription.add(subscription);
    }

    public void getListStatuses() {
        Subscription subscription = mStatusRepository.getListStatus()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mViewModel.showProgressbar();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Status>>() {
                    @Override
                    public void call(List<Status> statuses) {
                        mViewModel.onDeviceStatusLoaded(statuses);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mViewModel.onInputStatusError();
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        mViewModel.hideProgressbar();
                    }
                });
        mCompositeSubscription.add(subscription);
    }

    @Override
    public boolean validateDataInput(Device device) {
        boolean isValid = true;
        if (TextUtils.isEmpty(device.getDeviceCode())) {
            isValid = false;
            mViewModel.onInputDeviceCodeError();
        }
        if (TextUtils.isEmpty(device.getModelNumber())) {
            isValid = false;
            mViewModel.onInputModellNumberError();
        }
        if (TextUtils.isEmpty(device.getProductionName())) {
            isValid = false;
            mViewModel.onInputProductionNameError();
        }
        if (TextUtils.isEmpty(device.getSerialNumber())) {
            isValid = false;
            mViewModel.onInputSerialNumberError();
        }
        return isValid;
    }
}
