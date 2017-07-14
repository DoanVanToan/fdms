package com.framgia.fdms.screen.devicecreation;

import android.text.TextUtils;
import android.view.View;
import com.framgia.fdms.data.model.Category;
import com.framgia.fdms.data.model.Device;
import com.framgia.fdms.data.model.Status;
import com.framgia.fdms.data.source.BranchRepository;
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
    private BranchRepository mBranchRepository;

    public CreateDevicePresenter(CreateDeviceContract.ViewModel viewModel,
            DeviceRepository deviceRepository, StatusRepository statusRepository,
            CategoryRepository categoryRepository, BranchRepository branchRepository) {
        mViewModel = viewModel;
        mDeviceRepository = deviceRepository;
        mCategoryRepository = categoryRepository;
        mStatusRepository = statusRepository;
        mBranchRepository = branchRepository;
        mCompositeSubscription = new CompositeSubscription();
        getListCategories();
        getListStatuses();
        getListBranch();
    }

    @Override
    public void onStart() {
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
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mViewModel.showProgressbar();
                    }
                })
                .subscribe(new Action1<Device>() {
                    @Override
                    public void call(Device device) {
                        mViewModel.onRegisterSuccess();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mViewModel.hideProgressbar();
                        mViewModel.onLoadError(throwable.getMessage());
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
    public void updateDevice(final Device localDevice) {
        if (!validateDataEditDevice(localDevice)) return;
        mViewModel.setProgressBar(View.VISIBLE);
        Subscription subscription = mDeviceRepository.updateDevice(localDevice)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Device>() {
                    @Override
                    public void call(Device device) {
                        localDevice.cloneDevice(device);
                        mViewModel.onUpdateSuccess(localDevice);
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
                        mViewModel.hideProgressbar();
                        mViewModel.onLoadError(throwable.getMessage());
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
                        mViewModel.hideProgressbar();
                        mViewModel.onLoadError(throwable.getMessage());
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        mViewModel.hideProgressbar();
                    }
                });
        mCompositeSubscription.add(subscription);
    }

    public void getListBranch() {
        Subscription subscription = mBranchRepository.getListBranch()
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
                        mViewModel.onGetBranchSuccess(statuses);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mViewModel.hideProgressbar();
                        mViewModel.onLoadError(throwable.getMessage());
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
    public void getDeviceCode(int deviceCategoryId, int branchId) {
        Subscription subscription = mDeviceRepository.getDeviceCode(deviceCategoryId, branchId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mViewModel.showProgressbar();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Device>() {
                    @Override
                    public void call(Device device) {
                        if (device != null && device.getDeviceCode() != null) {
                            mViewModel.onGetDeviceCodeSuccess(device.getDeviceCode());
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mViewModel.hideProgressbar();
                        mViewModel.onLoadError(throwable.getMessage());
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
        if (device.getDeviceCategoryId() <= 0) {
            isValid = false;
            mViewModel.onInputCategoryError();
        }
        if (device.getBoughtDate() == null) {
            isValid = false;
            mViewModel.onInputBoughtDateError();
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
        if (TextUtils.isEmpty(device.getOriginalPrice())) {
            isValid = false;
            mViewModel.onInputOriginalPriceError();
        }
        return isValid;
    }

    @Override
    public boolean validateDataEditDevice(Device device) {
        boolean isValid = true;
        if (TextUtils.isEmpty(device.getProductionName())) {
            mViewModel.onInputProductionNameError();
            isValid = false;
        }
        return isValid;
    }
}
