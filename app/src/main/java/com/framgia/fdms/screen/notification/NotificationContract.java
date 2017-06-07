package com.framgia.fdms.screen.notification;

import com.framgia.fdms.BasePresenter;
import com.framgia.fdms.BaseViewModel;
import com.framgia.fdms.data.model.Notification;
import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface NotificationContract {
    /**
     * View.
     */
    interface ViewModel extends BaseViewModel<Presenter> {
        void onClickNotification(Notification notification, int position);

        void onLoadNotificationSuccess(List<Notification> notifications);

        void onLoadNotificationFails();
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter {
        void getNotifications();
    }
}
