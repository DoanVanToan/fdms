package com.framgia.fdms.screen.request;

import com.framgia.fdms.data.model.User;
import com.framgia.fdms.data.source.UserRepository;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Listens to user actions from the UI ({@link RequestFragment}), retrieves the data and updates
 * the UI as required.
 */
final class RequestPresenter implements RequestContract.Presenter {
    private static final String TAG = RequestPresenter.class.getName();

    private final RequestContract.ViewModel mViewModel;
    private UserRepository mRepository;
    private CompositeSubscription mCompositeSubscription;

    public RequestPresenter(RequestContract.ViewModel viewModel, UserRepository repository) {
        mViewModel = viewModel;
        mRepository = repository;
        mCompositeSubscription = new CompositeSubscription();
        getCurrentUser();
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
        mCompositeSubscription.clear();
    }

    @Override
    public void getCurrentUser() {
        Subscription subscription = mRepository.getCurrentUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<User>() {
                    @Override
                    public void call(User user) {
                        mViewModel.onGetCurrentUserSuccess(user);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mViewModel.onError(throwable.getMessage());
                    }
                });
        mCompositeSubscription.add(subscription);
    }
}
