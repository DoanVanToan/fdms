package com.framgia.fdms.screen.returndevice;

import com.framgia.fdms.data.model.Status;
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

    public ReturnDevicePresenter(ReturnDeviceContract.ViewModel viewModel,
            StatusRepository repository) {
        mViewModel = viewModel;
        mRepository = repository;
        mSubscription = new CompositeSubscription();
    }

    @Override
    public void getListAssign() {
        Subscription subscription = mRepository.getListAssignee()
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
    public void onStart() {
        getListAssign();
    }

    @Override
    public void onStop() {
        mSubscription.clear();
    }
}