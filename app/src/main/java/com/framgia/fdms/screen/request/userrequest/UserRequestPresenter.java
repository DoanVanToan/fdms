package com.framgia.fdms.screen.request.userrequest;

import com.framgia.fdms.data.model.Request;
import com.framgia.fdms.data.model.Respone;
import com.framgia.fdms.data.model.Status;
import com.framgia.fdms.data.model.User;
import com.framgia.fdms.data.source.RequestRepositoryContract;
import com.framgia.fdms.data.source.StatusRepository;
import com.framgia.fdms.data.source.UserRepository;
import java.util.List;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.framgia.fdms.screen.request.RequestPagerAdapter.RequestPage.USER_REQUEST;
import static com.framgia.fdms.utils.Constant.ALL_RELATIVE_ID;
import static com.framgia.fdms.utils.Constant.ALL_REQUEST_STATUS_ID;
import static com.framgia.fdms.utils.Constant.FIRST_PAGE;
import static com.framgia.fdms.utils.Constant.PER_PAGE;

/**
 * Listens to user actions from the UI ({@link UserRequestFragment}), retrieves the data and
 * updates
 * the UI as required.
 */
public final class UserRequestPresenter implements UserRequestContract.Presenter {

    private int mPage = FIRST_PAGE;
    private final UserRequestContract.ViewModel mViewModel;
    private CompositeSubscription mSubscription;
    private RequestRepositoryContract mRequestRepository;
    private StatusRepository mRepository;
    private UserRepository mUserRepository;
    private int mRelativeId = ALL_RELATIVE_ID;
    private int mStatusId = ALL_REQUEST_STATUS_ID;

    public UserRequestPresenter(UserRequestContract.ViewModel viewModel,
            RequestRepositoryContract deviceRepository, StatusRepository statusRepository,
            UserRepository userRepository) {
        mViewModel = viewModel;
        mSubscription = new CompositeSubscription();
        mRequestRepository = deviceRepository;
        mRepository = statusRepository;
        mUserRepository = userRepository;
        getStatusDevice();
        getListRelative();
        getMyRequest(ALL_REQUEST_STATUS_ID, ALL_RELATIVE_ID, mPage, PER_PAGE);
        getCurrentUser();
    }

    @Override
    public void onLoadMore() {
        mPage++;
        getMyRequest(mStatusId, mRelativeId, mPage, PER_PAGE);
    }

    @Override
    public void getData(Status relative, Status status) {
        mPage = FIRST_PAGE;
        if (relative != null) {
            mRelativeId = relative.getId();
        }
        if (status != null) {
            mStatusId = status.getId();
        }

        getMyRequest(mStatusId, mRelativeId, mPage, PER_PAGE);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        mSubscription.clear();
    }

    @Override
    public void getMyRequest(int requestStatusId, int relativeId, int perPage, int page) {
        mViewModel.setRefresh(true);
        Subscription subscription =
                mRequestRepository.getRequests(USER_REQUEST, requestStatusId, relativeId, page,
                        perPage)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                mViewModel.showProgressbar();
                            }
                        })
                        .subscribe(new Subscriber<List<Request>>() {
                            @Override
                            public void onCompleted() {
                                mViewModel.hideProgressbar();
                                mViewModel.setRefresh(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                mViewModel.hideProgressbar();
                                mViewModel.setRefresh(false);
                                mViewModel.onGetRequestError();
                            }

                            @Override
                            public void onNext(List<Request> requests) {
                                mViewModel.onGetRequestSuccess(requests);
                            }
                        });
        mSubscription.add(subscription);
    }

    @Override
    public void getStatusDevice() {
        Subscription subscription = mRepository.getListStatusRequest()
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
                        mViewModel.onGetStatusSuccess(statuses);
                    }
                });

        mSubscription.add(subscription);
    }

    @Override
    public void getListRelative() {
        Subscription subscription = mRepository.getListRelative()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Status>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mViewModel.onLoadError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<Status> statuses) {
                        mViewModel.onGetRelativeSuccess(statuses);
                    }
                });

        mSubscription.add(subscription);
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
                        mViewModel.onLoadError(throwable.getMessage());
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        mViewModel.hideProgressbar();
                    }
                });

        mSubscription.add(subscription);
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
                        mViewModel.onLoadError(throwable.getMessage());
                    }
                });
        mSubscription.add(subscription);
    }
}
