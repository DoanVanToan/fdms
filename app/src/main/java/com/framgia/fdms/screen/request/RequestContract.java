package com.framgia.fdms.screen.request;

import android.content.Intent;
import android.support.v4.view.ViewPager;

import com.framgia.fdms.BasePresenter;
import com.framgia.fdms.BaseViewModel;
import com.framgia.fdms.data.model.User;

/**
 * This specifies the contract between the view and the presenter.
 */
interface RequestContract {
    /**
     * View.
     */
    interface ViewModel extends BaseViewModel<Presenter> {
        void onRegisterRequestClick();

        void onGetCurrentUserSuccess(User user);

        void onError(String message);

        void onActivityResult(int requestCode, int resultCode, Intent data);

        void onClickChangeTab(ViewPager viewPager, int currentTab);

        void onShowCase();
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter {
        void getCurrentUser();

        void saveShowCase();
    }
}
