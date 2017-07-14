package com.framgia.fdms.screen.notification;

import com.framgia.fdms.data.model.Notification;
import com.framgia.fdms.data.source.NotificationRepository;
import java.util.List;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Listens to user actions from the UI ({@link NotificationActivity}), retrieves the data and
 * updates
 * the UI as required.
 */
public final class NotificationPresenter implements NotificationContract.Presenter {
    private static final String TAG = NotificationPresenter.class.getName();

    private final NotificationContract.ViewModel mViewModel;
    private NotificationRepository mRepository = NotificationRepository.getInstances();
    private CompositeSubscription mSubscription;

    public NotificationPresenter(NotificationContract.ViewModel viewModel) {
        mViewModel = viewModel;
        mSubscription = new CompositeSubscription();
    }

    @Override
    public void onStart() {
        getNotifications();
    }

    @Override
    public void onStop() {
        mSubscription.clear();
    }

    @Override
    public void getNotifications() {
        Subscription subscription = mRepository.getNotifications()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                    }
                })
                .subscribe(new Subscriber<List<Notification>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mViewModel.onLoadNotificationFails();
                    }

                    @Override
                    public void onNext(List<Notification> notifications) {
                        mViewModel.onLoadNotificationSuccess(notifications);
                    }
                });
        mSubscription.add(subscription);
    }
}
