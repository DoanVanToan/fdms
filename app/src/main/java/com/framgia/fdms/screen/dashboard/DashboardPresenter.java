package com.framgia.fdms.screen.dashboard;

import com.framgia.fdms.data.model.User;
import com.framgia.fdms.data.source.UserRepository;
import com.framgia.fdms.data.source.local.UserLocalDataSource;
import com.framgia.fdms.data.source.local.sharepref.SharePreferenceApi;
import com.framgia.fdms.data.source.local.sharepref.SharePreferenceImp;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.framgia.fdms.data.source.local.sharepref.SharePreferenceKey.IS_SHOW_CASE_MAIN;

/**
 * Listens to user actions from the UI ({@link DashboardFragment}), retrieves the data and updates
 * the UI as required.
 */
public class DashboardPresenter implements DashboardContract.Presenter {
    private static final String TAG = DashboardPresenter.class.getName();
    private final DashboardContract.ViewModel mViewModel;
    private UserRepository mRepository;
    private CompositeSubscription mCompositeSubscription;
    private SharePreferenceApi mSharedPreferences;

    public DashboardPresenter(DashboardContract.ViewModel viewModel, UserRepository repository,
                              SharePreferenceApi sharedPreferences) {
        mViewModel = viewModel;
        mRepository = repository;
        mCompositeSubscription = new CompositeSubscription();
        mSharedPreferences = sharedPreferences;
    }

    @Override
    public void onStart() {
        getCurrentUser();
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

    @Override
    public void saveShowCase() {
        mSharedPreferences.put(IS_SHOW_CASE_MAIN, true);
    }
}
