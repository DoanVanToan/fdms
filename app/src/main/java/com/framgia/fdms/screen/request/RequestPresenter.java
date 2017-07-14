package com.framgia.fdms.screen.request;

import com.framgia.fdms.data.model.User;
import com.framgia.fdms.data.source.UserRepository;
import com.framgia.fdms.data.source.local.sharepref.SharePreferenceApi;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.framgia.fdms.data.source.local.sharepref.SharePreferenceKey.IS_SHOW_CASE_REQUEST;

/**
 * Listens to user actions from the UI ({@link RequestFragment}), retrieves the data and updates
 * the UI as required.
 */
public class RequestPresenter implements RequestContract.Presenter {
    private static final String TAG = RequestPresenter.class.getName();
    private final RequestContract.ViewModel mViewModel;
    private UserRepository mRepository;
    private CompositeSubscription mCompositeSubscription;
    private SharePreferenceApi mSharedPreferences;

    public RequestPresenter(RequestContract.ViewModel viewModel, UserRepository repository,
                            SharePreferenceApi sharedPreferences) {
        mViewModel = viewModel;
        mRepository = repository;
        mCompositeSubscription = new CompositeSubscription();
        getCurrentUser();
        mSharedPreferences = sharedPreferences;
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

    @Override
    public void saveShowCase() {
        mSharedPreferences.put(IS_SHOW_CASE_REQUEST, true);
    }
}
