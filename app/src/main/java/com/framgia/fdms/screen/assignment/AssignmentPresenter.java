package com.framgia.fdms.screen.assignment;

import com.framgia.fdms.data.model.Request;
import com.framgia.fdms.data.source.RequestRepository;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Listens to user actions from the UI ({@link AssignmentActivity}), retrieves the data and updates
 * the UI as required.
 */
final class AssignmentPresenter implements AssignmentContract.Presenter {
    private static final String TAG = AssignmentPresenter.class.getName();

    private final AssignmentContract.ViewModel mViewModel;
    private int mRequestId;
    private RequestRepository mRequestRepository;
    private CompositeSubscription mSubscription;

    public AssignmentPresenter(AssignmentContract.ViewModel viewModel, int requestId,
            RequestRepository requestRepository) {
        mViewModel = viewModel;
        mRequestId = requestId;
        mRequestRepository = requestRepository;
        mSubscription = new CompositeSubscription();
        getRequest(mRequestId);
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
        mSubscription.clear();
    }

    @Override
    public void registerAssignment(Request request) {
        Subscription subscription = mRequestRepository.getRequest(mRequestId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<Request>() {
                    @Override
                    public void call(Request request) {
                        mViewModel.onGetRequestSuccess(request);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mViewModel.onLoadError(throwable.getMessage());
                    }
                });
        mSubscription.add(subscription);
    }

    @Override
    public void getRequest(int requestId) {
        Subscription subscription = mRequestRepository.getRequest(requestId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<Request>() {
                    @Override
                    public void call(Request request) {
                        mViewModel.onGetRequestSuccess(request);
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
