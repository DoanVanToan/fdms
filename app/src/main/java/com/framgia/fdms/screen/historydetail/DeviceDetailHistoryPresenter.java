package com.framgia.fdms.screen.historydetail;

import com.framgia.fdms.data.model.DeviceHistoryDetail;
import com.framgia.fdms.data.source.DeviceRepository;
import java.util.List;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Listens to user actions from the UI ({@link DeviceDetailHistoryFragment}), retrieves the data and
 * updates
 * the UI as required.
 */
public final class DeviceDetailHistoryPresenter implements DeviceDetailHistoryContract.Presenter {
    private static final String TAG = DeviceDetailHistoryPresenter.class.getName();

    private final DeviceDetailHistoryContract.ViewModel mViewModel;
    private DeviceRepository mRepository;
    private int mDeviceId = -1;

    public DeviceDetailHistoryPresenter(DeviceDetailHistoryContract.ViewModel viewModel,
            DeviceRepository repository, int deviceId) {
        mViewModel = viewModel;
        mRepository = repository;
        mDeviceId = deviceId;
    }

    @Override
    public void onStart() {
        getDetailHistory(mDeviceId);
    }

    @Override
    public void onStop() {
    }

    @Override
    public void getDetailHistory(int deviceId) {
        mRepository.getDeviceDetailHistory(deviceId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<DeviceHistoryDetail>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mViewModel.onGetDeviceHistoryFailed(e.getMessage());
                    }

                    @Override
                    public void onNext(List<DeviceHistoryDetail> deviceHistoryDetails) {
                        mViewModel.onGetDeviceHistorySuccess(deviceHistoryDetails);
                    }
                });
    }
}
