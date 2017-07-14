package com.framgia.fdms.screen.profile;

import android.content.Intent;
import com.framgia.fdms.BasePresenter;
import com.framgia.fdms.BaseViewModel;
import com.framgia.fdms.data.model.User;

/**
 * This specifies the contract between the view and the presenter.
 */
interface ProfileContract {
    /**
     * View.
     */
    interface ViewModel extends BaseViewModel<Presenter> {
        void onClickEditProfile();

        void onClickPickAvatar();

        void onClickDoneEditProfile();

        void onClickLogout();

        void onPickDateTime();

        void onActivityResult(int requestCode, int resultCode, Intent data);

        void setCurrentUser(User user);

        void onError(String message);
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter {
        void getCurrentUser();
    }
}
