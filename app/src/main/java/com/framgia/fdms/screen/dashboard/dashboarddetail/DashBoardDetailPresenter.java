package com.framgia.fdms.screen.dashboard.dashboarddetail;

import com.framgia.fdms.data.model.Dashboard;
import com.framgia.fdms.data.model.Device;
import com.framgia.fdms.data.model.Request;
import com.framgia.fdms.data.source.DeviceRepository;
import com.framgia.fdms.data.source.RequestRepository;
import java.util.List;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.framgia.fdms.screen.dashboard.dashboarddetail.DashBoardDetailFragment
        .DEVICE_DASHBOARD;
import static com.framgia.fdms.screen.dashboard.dashboarddetail.DashBoardDetailFragment
        .REQUEST_DASHBOARD;

/**
 * Listens to user actions from the UI ({@link DashBoardDetailFragment}), retrieves the data and
 * updates
 * the UI as required.
 */
final class DashBoardDetailPresenter implements DashBoardDetailContract.Presenter {
    private CompositeSubscription mCompositeSubscriptions = new CompositeSubscription();

    private final DashBoardDetailContract.ViewModel mViewModel;
    private DeviceRepository mDeviceRepository;
    private RequestRepository mRequestRepository;
    private int mDashboardType;
    public static final int top = 1;

    public DashBoardDetailPresenter(DashBoardDetailContract.ViewModel viewModel,
            DeviceRepository deviceRepository, RequestRepository requestRepository,
            int dashboardType) {
        mViewModel = viewModel;
        mDeviceRepository = deviceRepository;
        mRequestRepository = requestRepository;
        mDashboardType = dashboardType;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
        mCompositeSubscriptions.clear();
    }

    @Override
    public void getDeviceDashboard() {
        Subscription subscription = mDeviceRepository.getDashboardDevice()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Dashboard>>() {
                    @Override
                    public void call(List<Dashboard> dashboards) {
                        mViewModel.onDashBoardLoaded(dashboards);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mViewModel.onDashBoardError(throwable.getMessage());
                    }
                });
        mCompositeSubscriptions.add(subscription);
    }

    @Override
    public void getRequestDashboard() {
        Subscription subscription = mRequestRepository.getDashboardRequest()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Dashboard>>() {
                    @Override
                    public void call(List<Dashboard> dashboards) {
                        mViewModel.onDashBoardLoaded(dashboards);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mViewModel.onDashBoardError(throwable.getMessage());
                    }
                });
        mCompositeSubscriptions.add(subscription);
    }

    @Override
    public void getData() {
        if (mDashboardType == DEVICE_DASHBOARD) {
            getDeviceDashboard();
            getTopDevice();
        } else if (mDashboardType == REQUEST_DASHBOARD) {
            getRequestDashboard();
            getTopRequest();
        }
    }

    @Override
    public void getTopRequest() {
        Subscription subscription = mRequestRepository.getTopRequest(top)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Request>>() {
                    @Override
                    public void call(List<Request> requests) {
                        mViewModel.onGetTopRequestSuccess(requests);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mViewModel.onDashBoardError(throwable.getMessage());
                    }
                });
        mCompositeSubscriptions.add(subscription);
    }

    @Override
    public void getTopDevice() {
        Subscription subscription = mDeviceRepository.getTopDevice(top)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Device>>() {
                    @Override
                    public void call(List<Device> devices) {
                        mViewModel.onGetTopDeviceSuccess(devices);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mViewModel.onDashBoardError(throwable.getMessage());
                    }
                });
        mCompositeSubscriptions.add(subscription);
    }
}
