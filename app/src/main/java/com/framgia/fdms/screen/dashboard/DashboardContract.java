package com.framgia.fdms.screen.dashboard;

import com.framgia.fdms.BasePresenter;
import com.framgia.fdms.BaseViewModel;
import com.framgia.fdms.data.model.User;

/**
 * This specifies the contract between the view and the presenter.
 */
interface DashboardContract {
    /**
     * View.
     */
    interface ViewModel extends BaseViewModel<Presenter> {
        void setupViewPager(User user);

        void onError(String message);

        void onStartNotificationView();
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter {
        void getCurrentUser();
    }
}
