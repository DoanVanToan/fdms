package com.framgia.fdms.screen.dashboard.dashboarddetail;

import com.framgia.fdms.data.model.Dashboard;
import com.framgia.fdms.data.model.Device;
import com.framgia.fdms.data.model.Request;
import com.framgia.fdms.data.model.Respone;
import com.framgia.fdms.data.model.User;
import com.framgia.fdms.data.source.DeviceRepository;
import com.framgia.fdms.data.source.RequestRepository;
import com.framgia.fdms.data.source.UserRepository;
import java.util.List;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
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
public final class DashBoardDetailPresenter implements DashBoardDetailContract.Presenter {
    private CompositeSubscription mCompositeSubscriptions = new CompositeSubscription();

    private final DashBoardDetailContract.ViewModel mViewModel;
    private DeviceRepository mDeviceRepository;
    private RequestRepository mRequestRepository;
    private UserRepository mUserRepository;
    private int mDashboardType;
    public static final int top = 1;

    public DashBoardDetailPresenter(DashBoardDetailContract.ViewModel viewModel,
            DeviceRepository deviceRepository, RequestRepository requestRepository,
            int dashboardType, UserRepository userRepository) {
        mViewModel = viewModel;
        mDeviceRepository = deviceRepository;
        mRequestRepository = requestRepository;
        mDashboardType = dashboardType;
        mUserRepository = userRepository;
        getCurrentUser();
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
                        mViewModel.setRefresh(false);
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
                        mViewModel.setRefresh(false);
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
        mViewModel.setRefresh(true);
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
                        mViewModel.setRefresh(false);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mViewModel.onDashBoardError(throwable.getMessage());
                        mViewModel.setRefresh(false);
                    }
                });
        mCompositeSubscriptions.add(subscription);
    }

    @Override
    public void updateActionRequest(int requestId, int actionId) {
        Subscription subscription = mRequestRepository.updateActionRequest(requestId, actionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mViewModel.showProgressbar();
                    }
                })
                .subscribe(new Action1<Respone<Request>>() {
                    @Override
                    public void call(Respone<Request> requestRespone) {
                        mViewModel.onUpdateActionSuccess(requestRespone);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mViewModel.hideProgressbar();
                        mViewModel.onDashBoardError(throwable.getMessage());
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        mViewModel.hideProgressbar();
                    }
                });

        mCompositeSubscriptions.add(subscription);
    }

    @Override
    public void getCurrentUser() {
        Subscription subscription = mUserRepository.getCurrentUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<User>() {
                    @Override
                    public void call(User user) {
                        mViewModel.setCurrentUser(user);
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
