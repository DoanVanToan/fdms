package com.framgia.fdms.screen.device;

import com.framgia.fdms.data.model.User;
import com.framgia.fdms.data.source.UserRepository;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Listens to user actions from the UI ({@link DeviceFragment}), retrieves the data and updates
 * the UI as required.
 */
final class DevicePresenter implements DeviceContract.Presenter {
    private static final String TAG = DevicePresenter.class.getName();

    private final DeviceContract.ViewModel mViewModel;
    private UserRepository mUserRepository;
    private CompositeSubscription mCompositeSubscription;

    public DevicePresenter(DeviceContract.ViewModel viewModel, UserRepository userRepository) {
        mViewModel = viewModel;
        mUserRepository = userRepository;
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
        Subscription subscription = mUserRepository.getCurrentUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<User>() {
                    @Override
                    public void call(User user) {
                        mViewModel.setupViewPager(user);
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
