package com.framgia.fdms.screen.authenication.login;

import com.framgia.fdms.BasePresenter;
import com.framgia.fdms.BaseViewModel;

/**
 * This specifies the contract between the view and the presenter.
 */
interface LoginContract {

    interface ViewModel extends BaseViewModel<Presenter> {
        void onLoginError(String msg);

        void onLoginSuccess();

        void onInputUserNameError();

        void onInputPasswordError();

        void showProgressbar();

        void hideProgressbar();

        void onCachedAccountLoaded(String user, String passWord);

        boolean isRememberAccount();
    }

    interface Presenter extends BasePresenter {
        void login(String userName, String passWord);

        boolean validateDataInput(String username, String password);
    }
}
